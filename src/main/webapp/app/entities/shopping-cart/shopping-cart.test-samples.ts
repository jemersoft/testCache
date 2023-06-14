import { OrderStatus } from 'app/entities/enumerations/order-status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

import { IShoppingCart, NewShoppingCart } from './shopping-cart.model';

export const sampleWithRequiredData: IShoppingCart = {
  id: 126,
  status: OrderStatus['COMPLETED'],
  totalPrice: 58349,
  paymentMethod: PaymentMethod['CREDIT_CARD'],
};

export const sampleWithPartialData: IShoppingCart = {
  id: 88085,
  status: OrderStatus['PAID'],
  totalPrice: 30745,
  paymentMethod: PaymentMethod['CREDIT_CARD'],
};

export const sampleWithFullData: IShoppingCart = {
  id: 96105,
  status: OrderStatus['PAID'],
  totalPrice: 62733,
  paymentMethod: PaymentMethod['IDEAL'],
  paymentReference: 'Upgradable Unions Texas',
};

export const sampleWithNewData: NewShoppingCart = {
  status: OrderStatus['REFUNDED'],
  totalPrice: 17615,
  paymentMethod: PaymentMethod['CREDIT_CARD'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
