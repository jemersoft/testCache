import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CustomerDetailsFormService, CustomerDetailsFormGroup } from './customer-details-form.service';
import { ICustomerDetails } from '../customer-details.model';
import { CustomerDetailsService } from '../service/customer-details.service';
import { Gender } from 'app/entities/enumerations/gender.model';

@Component({
  selector: 'jhi-customer-details-update',
  templateUrl: './customer-details-update.component.html',
})
export class CustomerDetailsUpdateComponent implements OnInit {
  isSaving = false;
  customerDetails: ICustomerDetails | null = null;
  genderValues = Object.keys(Gender);

  editForm: CustomerDetailsFormGroup = this.customerDetailsFormService.createCustomerDetailsFormGroup();

  constructor(
    protected customerDetailsService: CustomerDetailsService,
    protected customerDetailsFormService: CustomerDetailsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerDetails }) => {
      this.customerDetails = customerDetails;
      if (customerDetails) {
        this.updateForm(customerDetails);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerDetails = this.customerDetailsFormService.getCustomerDetails(this.editForm);
    if (customerDetails.id !== null) {
      this.subscribeToSaveResponse(this.customerDetailsService.update(customerDetails));
    } else {
      this.subscribeToSaveResponse(this.customerDetailsService.create(customerDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(customerDetails: ICustomerDetails): void {
    this.customerDetails = customerDetails;
    this.customerDetailsFormService.resetForm(this.editForm, customerDetails);
  }
}
