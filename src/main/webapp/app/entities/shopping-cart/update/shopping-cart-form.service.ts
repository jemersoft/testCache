import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShoppingCart, NewShoppingCart } from '../shopping-cart.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShoppingCart for edit and NewShoppingCartFormGroupInput for create.
 */
type ShoppingCartFormGroupInput = IShoppingCart | PartialWithRequiredKeyOf<NewShoppingCart>;

type ShoppingCartFormDefaults = Pick<NewShoppingCart, 'id'>;

type ShoppingCartFormGroupContent = {
  id: FormControl<IShoppingCart['id'] | NewShoppingCart['id']>;
  status: FormControl<IShoppingCart['status']>;
  totalPrice: FormControl<IShoppingCart['totalPrice']>;
  paymentMethod: FormControl<IShoppingCart['paymentMethod']>;
  paymentReference: FormControl<IShoppingCart['paymentReference']>;
  customerDetails: FormControl<IShoppingCart['customerDetails']>;
};

export type ShoppingCartFormGroup = FormGroup<ShoppingCartFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShoppingCartFormService {
  createShoppingCartFormGroup(shoppingCart: ShoppingCartFormGroupInput = { id: null }): ShoppingCartFormGroup {
    const shoppingCartRawValue = {
      ...this.getFormDefaults(),
      ...shoppingCart,
    };
    return new FormGroup<ShoppingCartFormGroupContent>({
      id: new FormControl(
        { value: shoppingCartRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      status: new FormControl(shoppingCartRawValue.status, {
        validators: [Validators.required],
      }),
      totalPrice: new FormControl(shoppingCartRawValue.totalPrice, {
        validators: [Validators.required, Validators.min(0)],
      }),
      paymentMethod: new FormControl(shoppingCartRawValue.paymentMethod, {
        validators: [Validators.required],
      }),
      paymentReference: new FormControl(shoppingCartRawValue.paymentReference),
      customerDetails: new FormControl(shoppingCartRawValue.customerDetails, {
        validators: [Validators.required],
      }),
    });
  }

  getShoppingCart(form: ShoppingCartFormGroup): IShoppingCart | NewShoppingCart {
    return form.getRawValue() as IShoppingCart | NewShoppingCart;
  }

  resetForm(form: ShoppingCartFormGroup, shoppingCart: ShoppingCartFormGroupInput): void {
    const shoppingCartRawValue = { ...this.getFormDefaults(), ...shoppingCart };
    form.reset(
      {
        ...shoppingCartRawValue,
        id: { value: shoppingCartRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ShoppingCartFormDefaults {
    return {
      id: null,
    };
  }
}
