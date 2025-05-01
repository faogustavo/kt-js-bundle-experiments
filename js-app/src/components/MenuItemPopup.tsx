'use client';
import React, { useState } from 'react';
import { ItemResponse } from 'kt-js-experiment';
import { Dialog } from '@headlessui/react';
import Image from 'next/image';
import { XMarkIcon, PlusIcon, MinusIcon } from '@heroicons/react/24/outline';

interface MenuItemPopupProps {
  item: ItemResponse;
  merchantId: string;
  merchantName: string;
  merchantDeliveryFee: number;
  merchantCategory: string;
  merchantDeliveryTime: number;
  onDismiss: () => void;
}

// Helper function to format price from cents to dollars
const formatPrice = (price: number): string => {
  return `$${(price / 100).toFixed(2)}`;
};

const MenuItemPopup: React.FC<MenuItemPopupProps> = ({
  item,
  merchantId,
  merchantName,
  merchantDeliveryFee,
  merchantCategory,
  merchantDeliveryTime,
  onDismiss
}) => {
  const [quantity, setQuantity] = useState(1);
  
  // Track selected options for each option group
  const [selectedOptions, setSelectedOptions] = useState<Record<string, any>>(() => {
    const initialOptions: Record<string, any> = {};
    
    item.options.forEach(option => {
      switch (option.type) {
        case 'Boolean':
          initialOptions[option.id] = null;
          break;
        case 'SingleSelection':
          // Default to first option with price 0 if available
          initialOptions[option.id] = option.options.find(entry => entry.price === 0) || null;
          break;
        case 'MultipleSelection':
          initialOptions[option.id] = new Set();
          break;
      }
    });
    
    return initialOptions;
  });
  
  // Calculate total price based on item price, options, and quantity
  const calculateTotalPrice = (): number => {
    let price = item.price;
    
    // Add option prices
    Object.keys(selectedOptions).forEach(optionId => {
      const selection = selectedOptions[optionId];
      
      if (!selection) return;
      
      if (selection instanceof Set) {
        // Handle set of multiple selections
        selection.forEach((entry: ItemResponse.OptionResponse.EntryResponse) => {
          price += entry.price;
        });
      } else {
        // Handle single selection
        price += selection.price;
      }
    });
    
    // Multiply by quantity
    return price * quantity;
  };
  
  const totalPrice = calculateTotalPrice();
  
  // Handle option selection/deselection
  const handleOptionChange = (
    option: ItemResponse.OptionResponse,
    entry: ItemResponse.OptionResponse.EntryResponse
  ) => {
    switch (option.type) {
      case 'Boolean':
        setSelectedOptions({
          ...selectedOptions,
          [option.id]: selectedOptions[option.id] === entry ? null : entry
        });
        break;
        
      case 'SingleSelection':
        setSelectedOptions({
          ...selectedOptions,
          [option.id]: entry
        });
        break;
        
      case 'MultipleSelection':
        const currentSet = selectedOptions[option.id] as Set<ItemResponse.OptionResponse.EntryResponse>;
        const newSet = new Set(currentSet);
        
        if (currentSet.has(entry)) {
          newSet.delete(entry);
        } else {
          newSet.add(entry);
        }
        
        setSelectedOptions({
          ...selectedOptions,
          [option.id]: newSet
        });
        break;
    }
  };
  
  const isSelected = (
    option: ItemResponse.OptionResponse,
    entry: ItemResponse.OptionResponse.EntryResponse
  ): boolean => {
    const selection = selectedOptions[option.id];
    
    if (!selection) return false;
    
    if (selection instanceof Set) {
      return selection.has(entry);
    }
    
    return selection.id === entry.id;
  };
  
  return (
    <Dialog
      open={true}
      onClose={onDismiss}
      className="relative z-50"
    >
      <div className="fixed inset-0 bg-black/30" aria-hidden="true" />
      
      <div className="fixed inset-0 flex items-end justify-center sm:items-center p-4">
        <Dialog.Panel className="mx-auto max-w-xl w-full rounded-t-lg sm:rounded-lg bg-white p-4 max-h-[85vh] overflow-auto flex flex-col">
          {/* Header with close button */}
          <div className="relative">
            <div className="aspect-video w-full rounded-lg overflow-hidden relative">
              <Image
                src={item.imageUrl}
                alt={item.name}
                fill
                style={{ objectFit: 'cover' }}
                className="rounded-lg"
              />
              
              <button
                onClick={onDismiss}
                className="absolute top-2 right-2 p-1 rounded-full bg-white/70 hover:bg-white"
              >
                <XMarkIcon className="h-6 w-6 text-gray-800" />
              </button>
            </div>
            
            <div className="mt-4">
              <h2 className="text-2xl font-bold">{item.name}</h2>
              <p className="text-gray-600 my-2">{item.description}</p>
              <p className="text-xl font-semibold">{formatPrice(item.price)}</p>
            </div>
          </div>
          
          <div className="mt-4 border-t border-gray-200 pt-4 flex-1 overflow-auto">
            {/* Options */}
            {item.options.map(option => (
              <div key={option.id} className="mb-6">
                <h3 className="text-lg font-semibold mb-2">{option.name}</h3>
                {option.description && (
                  <p className="text-gray-600 text-sm mb-3">{option.description}</p>
                )}
                
                <div className="space-y-2">
                  {option.options.map(entry => (
                    <div
                      key={entry.id}
                      className={`border rounded-md p-3 flex items-center ${
                        isSelected(option, entry)
                          ? 'border-blue-500 bg-blue-50'
                          : 'border-gray-200'
                      } ${entry.isAvailable ? 'cursor-pointer' : 'opacity-50'}`}
                      onClick={() => {
                        if (entry.isAvailable) {
                          handleOptionChange(option, entry);
                        }
                      }}
                    >
                      <div className="flex-1">
                        <p className="font-medium">{entry.name}</p>
                        {entry.description && (
                          <p className="text-gray-500 text-sm">{entry.description}</p>
                        )}
                      </div>
                      
                      {entry.price > 0 && (
                        <span className="ml-3 text-gray-700">+{formatPrice(entry.price)}</span>
                      )}
                      
                      {/* Different selection indicators based on option type */}
                      <div className="ml-3">
                        {option.type === 'Boolean' || option.type === 'SingleSelection' ? (
                          <div
                            className={`h-5 w-5 rounded-full border ${
                              isSelected(option, entry)
                                ? 'border-blue-500 bg-blue-500'
                                : 'border-gray-300'
                            } flex items-center justify-center`}
                          >
                            {isSelected(option, entry) && (
                              <div className="h-2 w-2 rounded-full bg-white" />
                            )}
                          </div>
                        ) : (
                          <div
                            className={`h-5 w-5 rounded border ${
                              isSelected(option, entry)
                                ? 'border-blue-500 bg-blue-500 text-white'
                                : 'border-gray-300'
                            } flex items-center justify-center`}
                          >
                            {isSelected(option, entry) && (
                              <svg className="h-3 w-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                              </svg>
                            )}
                          </div>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
            
            {/* Quantity selector */}
            <div className="border-t border-gray-200 py-4">
              <div className="flex justify-between items-center">
                <h3 className="text-lg font-semibold">Quantity</h3>
                
                <div className="flex items-center">
                  <button
                    onClick={() => quantity > 1 && setQuantity(quantity - 1)}
                    disabled={quantity <= 1}
                    className={`p-1 rounded-full border ${
                      quantity <= 1 ? 'border-gray-200 text-gray-300' : 'border-gray-300 text-gray-600'
                    }`}
                  >
                    <MinusIcon className="h-5 w-5" />
                  </button>
                  
                  <span className="mx-4 font-medium text-lg">{quantity}</span>
                  
                  <button
                    onClick={() => setQuantity(quantity + 1)}
                    className="p-1 rounded-full border border-gray-300 text-gray-600"
                  >
                    <PlusIcon className="h-5 w-5" />
                  </button>
                </div>
              </div>
            </div>
          </div>
          
          {/* Footer with total and add to cart button */}
          <div className="border-t border-gray-200 pt-4 mt-4">
            <div className="flex justify-between items-center mb-4">
              <span className="text-lg font-semibold">Total</span>
              <span className="text-xl font-bold">{formatPrice(totalPrice)}</span>
            </div>
            
            <button
              onClick={onDismiss}
              className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition"
            >
              Add to Cart
            </button>
          </div>
        </Dialog.Panel>
      </div>
    </Dialog>
  );
};

export default MenuItemPopup;
