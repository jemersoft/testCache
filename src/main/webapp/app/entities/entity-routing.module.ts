import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product',
        data: { pageTitle: 'Products' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'product-category',
        data: { pageTitle: 'ProductCategories' },
        loadChildren: () => import('./product-category/product-category.module').then(m => m.ProductCategoryModule),
      },
      {
        path: 'customer-details',
        data: { pageTitle: 'CustomerDetails' },
        loadChildren: () => import('./customer-details/customer-details.module').then(m => m.CustomerDetailsModule),
      },
      {
        path: 'shopping-cart',
        data: { pageTitle: 'ShoppingCarts' },
        loadChildren: () => import('./shopping-cart/shopping-cart.module').then(m => m.ShoppingCartModule),
      },
      {
        path: 'product-order',
        data: { pageTitle: 'ProductOrders' },
        loadChildren: () => import('./product-order/product-order.module').then(m => m.ProductOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
