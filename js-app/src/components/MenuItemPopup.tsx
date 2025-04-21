'use client';
import React, { useEffect, useState } from 'react';
import { ItemResponse } from 'kt-js-experiment';
import { CartError, useCart } from '@/context/CartContext';

interface MenuItemPopupProps {
  item: ItemResponse;
  cartItemId?: number;
  merchantId: string;
  merchantName: string;
  merchantDeliveryFee: number;
  merchantCategory?: string;
  merchantDeliveryTime?: number;
  initialQuantity?: number;
  initialOptions?: Record<string, boolean | string | string[] | null>;
  initialObservation?: string;
  isEdit?: boolean;
  onDismiss: () => void;
}

interface ItemOptionsProps {
  item: ItemResponse;
  selectedOptions: Record<string, boolean | string | string[] | null>;
  handleOptionChange: (optionId: string, value: boolean | string | string[] | null) => void;
}

interface BooleanOptionProps {
  option: ItemResponse.OptionResponse;
  selected: boolean;
  handleChange: (value: boolean) => void;
}

interface SingleSelectionOptionProps {
  option: ItemResponse.OptionResponse;
  selected: string | null;
  handleChange: (value: string) => void;
}

interface MultipleSelectionOptionProps {
  option: ItemResponse.OptionResponse;
  selected: string[];
  handleChange: (value: string[]) => void;
}

const BooleanOption: React.FC<BooleanOptionProps> = ({ option, selected, handleChange }) => (
  <div className="flex items-center">
    <input
      type="checkbox"
      id={ `option-${ option.id }` }
      className="h-4 w-4 text-blue-600 rounded border-gray-300 focus:ring-blue-500"
      checked={ selected }
      onChange={ (e) => handleChange(e.target.checked) }
      disabled={ !option.isAvailable }
    />
    <label htmlFor={ `option-${ option.id }` } className="ml-2 text-sm">
      { option.isAvailable ? 'Add this option' : 'Currently unavailable' }
    </label>
  </div>
);

const SingleSelectionOption: React.FC<SingleSelectionOptionProps> = ({ option, selected, handleChange }) => (
  <div className="space-y-2">
    { option.options.map((entry) => (
      <div key={ entry.id } className="flex items-center justify-between">
        <div className="flex items-center">
          <input
            type="radio"
            id={ `option-${ option.id }-${ entry.id }` }
            name={ `option-${ option.id }` }
            className="h-4 w-4 text-blue-600 border-gray-300 focus:ring-blue-500"
            checked={ selected === entry.id.toString() }
            onChange={ () => handleChange(entry.id.toString()) }
            disabled={ !option.isAvailable }
          />
          <label htmlFor={ `option-${ option.id }-${ entry.id }` } className="ml-2 text-sm">
            { entry.name }
            { entry.description && <span className="text-xs text-gray-500 ml-1">({ entry.description })</span> }
          </label>
        </div>
        { entry.price > 0 && <span className="text-sm">{ formatPrice(entry.price) }</span> }
      </div>
    )) }
  </div>
);

const MultipleSelectionOption: React.FC<MultipleSelectionOptionProps> = ({ option, selected, handleChange }) => (
  <div className="space-y-2">
    { option.options.map((entry) => (
      <div key={ entry.id } className="flex items-center justify-between">
        <div className="flex items-center">
          <input
            type="checkbox"
            id={ `option-${ option.id }-${ entry.id }` }
            className="h-4 w-4 text-blue-600 rounded border-gray-300 focus:ring-blue-500"
            checked={ selected.includes(entry.id.toString()) }
            onChange={ (e) => {
              const currentSelections = [...selected];
              if (e.target.checked) {
                handleChange([...currentSelections, entry.id.toString()]);
              } else {
                handleChange(currentSelections.filter((id) => id !== entry.id.toString()));
              }
            } }
            disabled={ !option.isAvailable }
          />
          <label htmlFor={ `option-${ option.id }-${ entry.id }` } className="ml-2 text-sm">
            { entry.name }
            { entry.description && <span className="text-xs text-gray-500 ml-1">({ entry.description })</span> }
          </label>
        </div>
        { entry.price > 0 && <span className="text-sm">{ formatPrice(entry.price) }</span> }
      </div>
    )) }
  </div>
);

const ItemOptions: React.FC<ItemOptionsProps> = ({ item, selectedOptions, handleOptionChange }) => {
  return (
    <div className="mb-6">
      <h4 className="font-semibold mb-2">Options</h4>
      <div className="space-y-4 max-h-60 overflow-y-auto pr-2">
        { item.options.map((option) => (
          <div key={ option.id } className="border-b pb-3">
            <div className="flex justify-between mb-1">
              <span className="font-medium">{ option.name }</span>
              { option.price > 0 && <span>{ formatPrice(option.price) }</span> }
            </div>
            { option.description && (
              <p className="text-sm text-gray-500 dark:text-gray-400 mb-2">{ option.description }</p>
            ) }

            { option.type === ItemResponse.OptionResponse.TypeResponse.Boolean && (
              <BooleanOption
                option={ option }
                selected={ !!selectedOptions[option.id] }
                handleChange={ (value) => handleOptionChange(option.id.toString(), value) }
              />
            ) }

            { option.type === ItemResponse.OptionResponse.TypeResponse.SingleSelection && (
              <SingleSelectionOption
                option={ option }
                selected={ selectedOptions[option.id] as string | null }
                handleChange={ (value) => handleOptionChange(option.id.toString(), value) }
              />
            ) }

            { option.type === ItemResponse.OptionResponse.TypeResponse.MultipleSelection && (
              <MultipleSelectionOption
                option={ option }
                selected={ Array.isArray(selectedOptions[option.id]) ? (selectedOptions[option.id] as string[]) : [] }
                handleChange={ (value) => handleOptionChange(option.id.toString(), value) }
              />
            ) }
          </div>
        )) }
      </div>
    </div>
  );
};

// Helper function to format price from cents to dollars
const formatPrice = (price: number): string => {
  return `$${ (price / 100).toFixed(2) }`;
};

const MenuItemPopup: React.FC<MenuItemPopupProps> = ({
                                                       item,
                                                       cartItemId,
                                                       merchantId,
                                                       merchantName,
                                                       merchantDeliveryFee,
                                                       merchantCategory,
                                                       merchantDeliveryTime,
                                                       initialQuantity = 1,
                                                       initialOptions = {},
                                                       initialObservation = '',
                                                       isEdit = false,
                                                       onDismiss,
                                                     }) => {
  const [quantity, setQuantity] = useState(initialQuantity);
  const [totalPrice, setTotalPrice] = useState(item.price);
  // Define a type for the selected options
  type OptionValue = boolean | string | string[] | null;
  const [selectedOptions, setSelectedOptions] = useState<Record<string, OptionValue>>(initialOptions);
  const [observation, setObservation] = useState(initialObservation); // New state for observation
  const [error, setError] = useState<CartError>(null); // New state for error

  const { addItem, updateItem, openCart } = useCart();

  // Calculate total price including selected options
  useEffect(() => {
    let optionsPrice = 0;

    // Calculate the price of selected options
    Object.entries(selectedOptions).forEach(([optionId, selection]) => {
      const option = item.options.find(opt => opt.id.toString() === optionId);
      if (!option) {
        return;
      }

      if (option.type === ItemResponse.OptionResponse.TypeResponse.Boolean && selection === true) {
        optionsPrice += option.price;
      } else if (option.type === ItemResponse.OptionResponse.TypeResponse.SingleSelection && selection !== null) {
        const selectedEntry = option.options.find(entry => entry.id.toString() === selection);
        if (selectedEntry) {
          optionsPrice += selectedEntry.price;
        }
      } else if (option.type === ItemResponse.OptionResponse.TypeResponse.MultipleSelection && Array.isArray(selection)) {
        selection?.forEach(entryId => {
          const selectedEntry = option.options.find(entry => entry.id.toString() === entryId);
          if (selectedEntry) {
            optionsPrice += selectedEntry.price;
          }
        });
      }
    });

    setTotalPrice(item.price + optionsPrice);
  }, [item, selectedOptions]);

  const handleIncrement = () => {
    setQuantity(prev => prev + 1);
  };

  const handleDecrement = () => {
    if (quantity > (isEdit ? 0 : 1)) {
      setQuantity(prev => prev - 1);
    }
  };

  const handleOptionChange = (optionId: string, value: OptionValue) => {
    setSelectedOptions(prev => ({
      ...prev,
      [optionId]: value,
    }));
  };

  const handleConfirm = () => {
    if (cartItemId) {
      setError(null); // Clear any previous error
      updateItem(cartItemId, quantity, selectedOptions, observation);
      onDismiss();
      return;
    }

    const result = addItem(
      item,
      merchantId,
      merchantName,
      quantity,
      selectedOptions,
      observation,
      merchantDeliveryFee,
      merchantCategory,
      merchantDeliveryTime,
    );

    if (result) {
      setError(result); // Store the error
    } else {
      setError(null); // Clear any previous error
      onDismiss();
      openCart();
    }
  };

  return (
    <>
      {/* Backdrop */ }
      <div className="fixed inset-0 bg-black/50 z-50 transition-opacity" onClick={ onDismiss }/>

      {/* Popup */ }
      <div className="fixed inset-0 z-[60] flex items-center justify-center p-4">
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-md w-full p-6"
             onClick={ e => e.stopPropagation() }>
          <div className="flex justify-between items-start mb-4">
            <h3 className="text-lg font-semibold">{ item.name }</h3>
            <button
              onClick={ onDismiss }
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

          {/* Display options if available */ }
          { item.options.length > 0 && (
            <ItemOptions
              item={ item }
              selectedOptions={ selectedOptions }
              handleOptionChange={ handleOptionChange }
            />
          ) }

          <div className="flex justify-between items-center mb-6">
            <span className="font-semibold">Total:</span>
            <span className="font-bold text-lg">{ formatPrice(totalPrice * quantity) }</span>
          </div>

          {/* Observation input */ }
          <div className="mb-4">
            <label htmlFor="observation" className="block text-sm font-medium text-gray-700 dark:text-gray-300">
              Observation
            </label>
            <textarea
              id="observation"
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:text-gray-300 p-2"
              style={ { height: '100px', resize: 'none' } } // Fixed height
              value={ observation }
              onChange={ (e) => setObservation(e.target.value) }
              placeholder="Add any special instructions or notes here..."
            />
          </div>

          { isEdit && quantity === 0 && (
            <div className="mb-4 p-2 bg-amber-100 dark:bg-amber-900/30 text-amber-700 dark:text-amber-300 rounded-md">
              Setting quantity to 0 will remove this item from your cart.
            </div>
          ) }

          {/* Display error message if present */ }
          { error && (
            <div className="mb-4 p-2 bg-red-100 dark:bg-red-900/30 text-red-700 dark:text-red-300 rounded-md">
              { error === 'different merchant' && 'You can only add items from the same merchant to your cart.' }
              { error === 'unknown error' && 'An unknown error occurred. Please try again.' }
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
