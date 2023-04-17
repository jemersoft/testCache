import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CustomerDetailsFormService } from './customer-details-form.service';
import { CustomerDetailsService } from '../service/customer-details.service';
import { ICustomerDetails } from '../customer-details.model';

import { CustomerDetailsUpdateComponent } from './customer-details-update.component';

describe('CustomerDetails Management Update Component', () => {
  let comp: CustomerDetailsUpdateComponent;
  let fixture: ComponentFixture<CustomerDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let customerDetailsFormService: CustomerDetailsFormService;
  let customerDetailsService: CustomerDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CustomerDetailsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CustomerDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustomerDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    customerDetailsFormService = TestBed.inject(CustomerDetailsFormService);
    customerDetailsService = TestBed.inject(CustomerDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const customerDetails: ICustomerDetails = { id: 456 };

      activatedRoute.data = of({ customerDetails });
      comp.ngOnInit();

      expect(comp.customerDetails).toEqual(customerDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerDetails>>();
      const customerDetails = { id: 123 };
      jest.spyOn(customerDetailsFormService, 'getCustomerDetails').mockReturnValue(customerDetails);
      jest.spyOn(customerDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customerDetails }));
      saveSubject.complete();

      // THEN
      expect(customerDetailsFormService.getCustomerDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(customerDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(customerDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerDetails>>();
      const customerDetails = { id: 123 };
      jest.spyOn(customerDetailsFormService, 'getCustomerDetails').mockReturnValue({ id: null });
      jest.spyOn(customerDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customerDetails }));
      saveSubject.complete();

      // THEN
      expect(customerDetailsFormService.getCustomerDetails).toHaveBeenCalled();
      expect(customerDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerDetails>>();
      const customerDetails = { id: 123 };
      jest.spyOn(customerDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(customerDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
