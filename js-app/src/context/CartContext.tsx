'use client';
import React, { createContext, ReactNode, useContext, useEffect, useState } from 'react';
import { ItemResponse } from 'kt-js-experiment';

// Define the cart item type with quantity
export interface CartItem {
  id: number,
  quantity: number;
  selectedOptions: Record<string, boolean | string | string[] | null>;
  readableOptions: Record<string, number>;
  subtotal: number;
  item: ItemResponse;
}

// Define the cart context type
interface CartContextType {
  items: CartItem[];
  isOpen: boolean;
  openCart: () => void;
  closeCart: () => void;
  toggleCart: () => void;
  addItem: (item: ItemResponse, merchantId: string, merchantName: string, quantity: number, selectedOptions: Record<string, boolean | string | string[] | null>, merchantDeliveryFee?: number, merchantCategory?: string, merchantDeliveryTime?: number) => void;
  removeItem: (itemId: number) => void;
  updateItem: (itemId: number, quantity: number, selectedOptions: Record<string, boolean | string | string[] | null>) => void;
  clearCart: () => void;
  totalItems: number;
  totalPrice: number;
  merchantDeliveryFee: number;
  merchantId: string | null;
  merchantName: string | null;
  merchantCategory: string | null;
  merchantDeliveryTime: number | null;
  error: string | null;
  clearError: () => void;
}

const generateReadableOptions = (
  item: ItemResponse,
  selectedOptions: Record<string, boolean | string | string[] | null>,
): Record<string, number> => {
  const readableOptions: Record<string, number> = {};

  Object.entries(selectedOptions).forEach(([key, value]) => {
    const option = item.options.find((opt) => opt.id.toString() === key);

    if (Array.isArray(value)) {
      // Multiple selection options
      value.forEach((optionId) => {
        const selectedOption = option?.options.find((opt) => opt.id.toString() === optionId);
        if (selectedOption) {
          readableOptions[selectedOption.name] = (readableOptions[selectedOption.name] || 0) + selectedOption.price;
        }
      });
    } else if (typeof value === 'string') {
      // Single selection options
      const selectedOption = option?.options.find((opt) => opt.id.toString() === value);
      if (selectedOption) {
        readableOptions[selectedOption.name] = selectedOption.price;
      }
    } else if (typeof value === 'boolean' && value) {
      // Boolean options
      if (option) {
        readableOptions[option.name] = option.price;
      }
    }
  });

  return readableOptions;
};

const calculateSubtotal = (
  item: ItemResponse,
  quantity: number,
  selectedOptions: Record<string, boolean | string | string[] | null>,
): number => {
  const basePrice = item.price;
  const optionsPrice = Object.entries(selectedOptions).reduce((total, [key, value]) => {
    if (Array.isArray(value)) {
      // Multiple selection options
      return total + value.reduce((sum, optionId) => {
        const option = item.options.find((opt) => opt.id.toString() === key);
        const selectedOption = option?.options.find((opt) => opt.id.toString() === optionId);
        return sum + (selectedOption?.price || 0);
      }, 0);
    } else if (typeof value === 'string') {
      // Single selection options
      const option = item.options.find((opt) => opt.id.toString() === key);
      const selectedOption = option?.options.find((opt) => opt.id.toString() === value);
      return total + (selectedOption?.price || 0);
    } else if (typeof value === 'boolean' && value) {
      // Boolean options
      const option = item.options.find((opt) => opt.id.toString() === key);
      return total + (option?.price || 0);
    }
    return total;
  }, 0);

  return (basePrice + optionsPrice) * quantity;
};

// Create the cart context with default values
const CartContext = createContext<CartContextType>({
  items: [],
  isOpen: false,
  openCart: () => {},
  closeCart: () => {},
  toggleCart: () => {},
  addItem: () => {},
  removeItem: () => {},
  updateItem: () => {},
  clearCart: () => {},
  totalItems: 0,
  totalPrice: 0,
  merchantDeliveryFee: 0,
  merchantId: null,
  merchantName: null,
  merchantCategory: null,
  merchantDeliveryTime: null,
  error: null,
  clearError: () => {},
});

// Custom hook to use the cart context
export const useCart = () => useContext(CartContext);

// Cart provider component
export const CartProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [items, setItems] = useState<CartItem[]>([]);
  const [isOpen, setIsOpen] = useState(false);
  const [totalItems, setTotalItems] = useState(0);
  const [totalPrice, setTotalPrice] = useState(0);
  const [merchantDeliveryFee, setMerchantDeliveryFee] = useState(0);
  const [merchantId, setMerchantId] = useState<string | null>(null);
  const [merchantName, setMerchantName] = useState<string | null>(null);
  const [merchantCategory, setMerchantCategory] = useState<string | null>(null);
  const [merchantDeliveryTime, setMerchantDeliveryTime] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  // Calculate totals whenever items change
  useEffect(() => {
    const itemCount = items.reduce((total, item) => total + item.quantity, 0);
    const price = items.reduce((total, cartItem) => total + cartItem.subtotal, 0);

    setTotalItems(itemCount);
    setTotalPrice(price);
  }, [items]);

  // Cart drawer functions
  const openCart = () => setIsOpen(true);
  const closeCart = () => setIsOpen(false);
  const toggleCart = () => setIsOpen(!isOpen);

  // Error handling
  const clearError = () => setError(null);

  // Cart item management functions
  const addItem = (
    item: ItemResponse,
    newMerchantId: string,
    newMerchantName: string,
    quantity: number = 1,
    selectedOptions: Record<string, boolean | string | string[] | null>,
    merchantDeliveryFee?: number,
    merchantCategory?: string,
    merchantDeliveryTime?: number,
  ) => {
    // Check if the cart is empty
    if (items.length === 0) {
      // Cart is empty, set the merchant ID and name
      setMerchantId(newMerchantId);
      setMerchantName(newMerchantName);
      if (merchantCategory) {
        setMerchantCategory(merchantCategory);
      }
      if (merchantDeliveryTime) {
        setMerchantDeliveryTime(merchantDeliveryTime);
      }
    } else if (newMerchantId !== merchantId) {
      // Cart is not empty and item is from a different merchant
      setError(`Your cart already has items from ${ merchantName }. Please clear your cart before adding items from ${ newMerchantName }.`);
      return;
    }

    // Clear any previous errors
    clearError();

    setItems(prevItems => {
      return [...prevItems, {
        id: Date.now(),
        quantity: quantity,
        selectedOptions: selectedOptions,
        readableOptions: generateReadableOptions(item, selectedOptions),
        subtotal: calculateSubtotal(item, quantity, selectedOptions),
        item: item,
      }];
    });

    // Update merchant delivery fee if provided
    if (merchantDeliveryFee !== undefined) {
      setMerchantDeliveryFee(merchantDeliveryFee);
    }

    // Open the cart drawer whenever an item is added
    openCart();
  };

  const removeItem = (itemId: number) => {
    setItems(prevItems => prevItems.filter(cartItem => cartItem.id !== itemId));
  };

  const updateItem = (itemId: number, quantity: number, selectedOptions: Record<string, boolean | string | string[] | null>) => {
    if (quantity <= 0) {
      removeItem(itemId);
      return;
    }

    setItems(prevItems =>
      prevItems.map(cartItem =>
        cartItem.id === itemId ?
          {
            ...cartItem,
            quantity,
            selectedOptions,
            readableOptions: generateReadableOptions(cartItem.item, selectedOptions),
            subtotal: calculateSubtotal(cartItem.item, quantity, selectedOptions),
          }
          : cartItem,
      )
    );
  };

  const clearCart = () => {
    setItems([]);
    setMerchantId(null);
    setMerchantName(null);
    setMerchantCategory(null);
    setMerchantDeliveryTime(null);
    setMerchantDeliveryFee(0);
  };

  // Provide the cart context to children components
  return (
    <CartContext.Provider
      value={ {
        items,
        isOpen,
        openCart,
        closeCart,
        toggleCart,
        addItem,
        removeItem,
        updateItem,
        clearCart,
        totalItems,
        totalPrice,
        merchantDeliveryFee,
        merchantId,
        merchantName,
        merchantCategory,
        merchantDeliveryTime,
        error,
        clearError,
      } }
    >
      { children }
    </CartContext.Provider>
  );
};

export default CartContext;
