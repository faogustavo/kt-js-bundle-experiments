'use client';
import React, { useState } from 'react';
import { ItemResponse } from 'kt-js-experiment';

interface MenuItemPopupProps {
  item: ItemResponse;
  merchantId: string;
  merchantName: string;
  merchantDeliveryFee: number;
  merchantCategory?: string;
  merchantDeliveryTime?: number;
  initialQuantity?: number;
  isEdit?: boolean;
  onConfirm: (item: ItemResponse, merchantId: string, merchantName: string, quantity: number, merchantDeliveryFee: number, merchantCategory?: string, merchantDeliveryTime?: number) => void;
  onCancel: () => void;
}

// Helper function to format price from cents to dollars
const formatPrice = (price: number): string => {
  return `$${ (price / 100).toFixed(2) }`;
};

const MenuItemPopup: React.FC<MenuItemPopupProps> = ({
                                                       item,
                                                       merchantId,
                                                       merchantName,
                                                       merchantDeliveryFee,
                                                       merchantCategory,
                                                       merchantDeliveryTime,
                                                       initialQuantity = 1,
                                                       isEdit = false,
                                                       onConfirm,
                                                       onCancel,
                                                     }) => {
  const [quantity, setQuantity] = useState(initialQuantity);

  const handleIncrement = () => {
    setQuantity(prev => prev + 1);
  };

  const handleDecrement = () => {
    if (quantity > (isEdit ? 0 : 1)) {
      setQuantity(prev => prev - 1);
    }
  };

  const handleConfirm = () => {
    onConfirm(item, merchantId, merchantName, quantity, merchantDeliveryFee, merchantCategory, merchantDeliveryTime);
  };

  return (
    <>
      {/* Backdrop */ }
      <div className="fixed inset-0 bg-black/50 z-50 transition-opacity" onClick={ onCancel }/>

      {/* Popup */ }
      <div className="fixed inset-0 z-[60] flex items-center justify-center p-4">
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-md w-full p-6"
             onClick={ e => e.stopPropagation() }>
          <div className="flex justify-between items-start mb-4">
            <h3 className="text-lg font-semibold">{ item.name }</h3>
            <button
              onClick={ onCancel }
              className="text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200"
              aria-label="Close popup"
            >
              <svg className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                <path fillRule="evenodd"
                      d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                      clipRule="evenodd"/>
              </svg>
            </button>
          </div>

          <p className="text-gray-600 dark:text-gray-400 mb-4">{ item.description }</p>
          <p className="font-medium mb-4">{ formatPrice(item.price) } each</p>

          <div className="flex justify-between items-center mb-6">
            <span className="font-semibold">Total:</span>
            <span className="font-bold text-lg">{ formatPrice(item.price * quantity) }</span>
          </div>

          { isEdit && quantity === 0 && (
            <div className="mb-4 p-2 bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-300 rounded-md">
              Setting quantity to 0 will remove this item from your cart.
            </div>
          ) }

          <div className="flex gap-2 items-center">
            <div className="flex items-center flex-1">
              <button
                onClick={ handleDecrement }
                className="p-2 rounded-full bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300"
                aria-label="Decrease quantity"
                disabled={ quantity <= (isEdit ? 0 : 1) }
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-5 w-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={ 2 }
                    d="M20 12H4"
                  />
                </svg>
              </button>
              <span className="mx-4 text-lg font-semibold">{ quantity }</span>
              <button
                onClick={ handleIncrement }
                className="p-2 rounded-full bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300"
                aria-label="Increase quantity"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-5 w-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={ 2 }
                    d="M12 4v16m8-8H4"
                  />
                </svg>
              </button>
            </div>
            <button
              onClick={ handleConfirm }
              className={ `flex-1 py-2 px-4 ${ quantity === 0 && isEdit ? 'bg-red-600 hover:bg-red-700' : 'bg-blue-600 hover:bg-blue-700' } text-white font-semibold rounded-md` }
            >
              { isEdit
                ? (quantity === 0 ? 'Remove Item' : 'Update Quantity')
                : 'Add to Cart' }
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default MenuItemPopup;
