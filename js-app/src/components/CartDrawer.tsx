'use client';
import React, { useState } from 'react';
import { CartItem, useCart } from '@/context/CartContext';
import MenuItemPopup from './MenuItemPopup';

// Helper function to format price from cents to dollars
const formatPrice = (price: number): string => {
  return `$${ (price / 100).toFixed(2) }`;
};

const CartDrawer: React.FC = () => {
  const {
    isOpen,
    closeCart,
    items,
    totalPrice,
    removeItem,
    clearCart,
    merchantDeliveryFee,
    merchantName,
    merchantCategory,
    merchantDeliveryTime,
  } = useCart();

  // Calculate total with delivery fee (only when there are items)
  const total = items.length > 0 ? totalPrice + merchantDeliveryFee : 0;

  const [showConfirmDialog, setShowConfirmDialog] = useState(false);
  const [editingItem, setEditingItem] = useState<CartItem | null>(null);

  const handleClearCart = () => {
    setShowConfirmDialog(true);
  };

  const confirmClearCart = () => {
    clearCart();
    setShowConfirmDialog(false);
    closeCart(); // Close the cart drawer after clearing the cart
  };

  const cancelClearCart = () => {
    setShowConfirmDialog(false);
  };

  const handleEditItem = (item: CartItem) => setEditingItem(item);
  const handleOnDismiss = () => setEditingItem(null)

  return (
    <>
      {/* Menu Item Popup */ }
      { editingItem && (
        <MenuItemPopup
          item={ editingItem.item }
          cartItemId={ editingItem.id }
          merchantId={ editingItem.item.id } // Using item ID as merchant ID since we don't need it for editing
          merchantName="" // Not needed for editing
          merchantDeliveryFee={ 0 } // Not needed for editing
          initialQuantity={ editingItem.quantity }
          initialOptions={ editingItem.selectedOptions }
          initialObservation={ editingItem.observation }
          isEdit={ true }
          onDismiss={ handleOnDismiss }
        />
      ) }

      {/* Confirmation Dialog */ }
      { showConfirmDialog && (
        <>
          <div className="fixed inset-0 bg-black/50 z-50 transition-opacity"/>
          <div className="fixed inset-0 z-[60] flex items-center justify-center p-4">
            <div 
              role="dialog"
              aria-modal="true"
              aria-label="Clear Cart Confirmation"
              aria-describedby="clear-cart-description"
              className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-md w-full p-6">
              <h3 className="text-lg font-semibold mb-4">Clear Cart</h3>
              <p id="clear-cart-description" className="mb-6">Are you sure you want to clear all items from your cart?</p>
              <div className="flex justify-end gap-2">
                <button
                  onClick={ cancelClearCart }
                  className="px-4 py-2 bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-gray-800 dark:text-gray-200 rounded-md"
                >
                  Cancel
                </button>
                <button
                  onClick={ confirmClearCart }
                  className="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded-md"
                >
                  Clear
                </button>
              </div>
            </div>
          </div>
        </>
      ) }

      {/* Backdrop */ }
      { isOpen && (
        <div
          className="fixed inset-0 bg-black/50 z-40 transition-opacity"
          onClick={ closeCart }
        />
      ) }

      {/* Drawer */ }
      <div
        role="dialog"
        aria-modal="true"
        aria-label="Shopping Cart"
        aria-describedby="cart-description"
        className={ `fixed top-0 right-0 h-full w-full sm:w-96 bg-white dark:bg-gray-800 shadow-xl z-50 transform transition-transform duration-300 ease-in-out ${
          isOpen ? 'translate-x-0' : 'translate-x-full'
        }` }
      >
        {/* Header */ }
        <div className="flex justify-between items-center p-4 border-b dark:border-gray-700">
          <div>
            <h2 className="text-xl font-semibold">Your Cart</h2>
            { items.length > 0 && merchantName && (
              <div id="cart-description" className="flex items-center gap-2 text-sm text-gray-600 dark:text-gray-300 mt-1">
                <span className="font-bold">{ merchantName }</span>
                { merchantCategory && (
                  <>
                    <span>•</span>
                    <span>{ merchantCategory }</span>
                  </>
                ) }
                { merchantDeliveryTime && (
                  <>
                    <span>•</span>
                    <span>{ merchantDeliveryTime } min delivery</span>
                  </>
                ) }
              </div>
            ) }
          </div>
          <button
            onClick={ closeCart }
            className="p-2 text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200"
            aria-label="Close cart"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={ 2 }
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
        </div>

        {/* Cart content */ }
        <div className="p-4 overflow-y-auto h-[calc(100%-8rem)]">
          { items.length === 0 ? (
            <div className="text-center py-8">
              <p className="text-gray-500 dark:text-gray-400">Your cart is empty</p>
            </div>
          ) : (
            <ul className="space-y-4" aria-label="Cart items">
              { items.map((cartItem) => (
                <li key={ cartItem.item.id } className="flex border-b dark:border-gray-700 pb-4">
                  <div className="flex-1">
                    <h3 className="font-medium">{ cartItem.item.name }</h3>
                    <p className="text-gray-500 dark:text-gray-400 text-sm">
                      { formatPrice(cartItem.item.price) } each
                    </p>
                    {/* Display readable options */ }
                    { Object
                      .entries(cartItem.readableOptions)
                      .sort(([, priceA], [, priceB]) => priceB - priceA)
                      .map(([optionName, price]) => (
                        <p key={ optionName } className="text-gray-500 dark:text-gray-400 text-sm">
                          { price > 0 ? `${ optionName } • ${ formatPrice(price) }` : optionName }
                        </p>
                      ))
                    }
                    {/* Display observation if available */ }
                    { cartItem.observation && (
                      <blockquote
                        className="mt-2 text-sm text-gray-600 dark:text-gray-400 border-l-4 border-gray-300 dark:border-gray-600 pl-2">
                        { cartItem.observation }
                      </blockquote>
                    ) }
                    <div className="flex items-center mt-2">
                      <span className="mr-2">Qty: { cartItem.quantity }</span>
                      <button
                        onClick={ () => handleEditItem(cartItem) }
                        className="px-2 py-1 text-sm bg-gray-200 hover:bg-gray-300 dark:bg-gray-700 dark:hover:bg-gray-600 text-gray-700 dark:text-gray-300 rounded"
                        aria-label="Edit quantity"
                      >
                        Edit
                      </button>
                    </div>
                  </div>
                  <div className="flex flex-col justify-between items-end">
                    <span className="font-medium">
                      { formatPrice(cartItem.subtotal) }
                    </span>
                    <button
                      onClick={ () => removeItem(cartItem.id) }
                      className="text-red-500 hover:text-red-700 text-sm"
                      aria-label="Remove item"
                    >
                      Remove
                    </button>
                  </div>
                </li>
              )) }
            </ul>
          ) }
        </div>

        {/* Footer */ }
        <div className="absolute bottom-0 left-0 right-0 p-4 border-t dark:border-gray-700 bg-white dark:bg-gray-800">
          <div className="flex flex-col gap-1 mb-4">
            {/* Subtotal */ }
            <div className="flex justify-between items-center">
              <span className="text-sm text-gray-500 dark:text-gray-400">Subtotal:</span>
              <span className="text-sm text-gray-500 dark:text-gray-400">{ formatPrice(totalPrice) }</span>
            </div>

            {/* Delivery Fee */ }
            <div className="flex justify-between items-center">
              <span className="text-sm text-gray-500 dark:text-gray-400">Delivery Fee:</span>
              <span className="text-sm text-gray-500 dark:text-gray-400">{ formatPrice(merchantDeliveryFee) }</span>
            </div>

            {/* Total */ }
            <div className="flex justify-between items-center mt-2">
              <span className="font-semibold">Total:</span>
              <span className="font-bold text-lg">{ formatPrice(total) }</span>
            </div>
          </div>
          <div className="flex gap-2 mb-2">
            <button
              onClick={ handleClearCart }
              className="flex-1 py-2 px-4 bg-red-600 hover:bg-red-700 text-white font-semibold rounded-md disabled:opacity-50 disabled:cursor-not-allowed"
              disabled={ items.length === 0 }
              aria-label="Clear cart"
            >
              Clear Cart
            </button>
          </div>
          <button
            className="w-full py-2 px-4 bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded-md disabled:opacity-50 disabled:cursor-not-allowed"
            disabled={ items.length === 0 }
          >
            Checkout
          </button>
        </div>
      </div>
    </>
  );
};

export default CartDrawer;

