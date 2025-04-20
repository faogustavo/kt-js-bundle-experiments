'use client';
import React, { createContext, ReactNode, useContext, useEffect, useState } from 'react';
import { ItemResponse } from 'kt-js-experiment';

// Define the cart item type with quantity
export interface CartItem {
  item: ItemResponse;
  quantity: number;
}

// Define the cart context type
interface CartContextType {
  items: CartItem[];
  isOpen: boolean;
  openCart: () => void;
  closeCart: () => void;
  toggleCart: () => void;
  addItem: (item: ItemResponse, merchantId: string, merchantName: string, merchantDeliveryFee?: number) => void;
  removeItem: (itemId: string) => void;
  updateQuantity: (itemId: string, quantity: number) => void;
  clearCart: () => void;
  totalItems: number;
  totalPrice: number;
  merchantDeliveryFee: number;
  merchantId: string | null;
  merchantName: string | null;
  error: string | null;
  clearError: () => void;
}

// Create the cart context with default values
const CartContext = createContext<CartContextType>({
  items: [],
  isOpen: false,
  openCart: () => {},
  closeCart: () => {},
  toggleCart: () => {},
  addItem: () => {},
  removeItem: () => {},
  updateQuantity: () => {},
  clearCart: () => {},
  totalItems: 0,
  totalPrice: 0,
  merchantDeliveryFee: 0,
  merchantId: null,
  merchantName: null,
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
  const [error, setError] = useState<string | null>(null);

  // Calculate totals whenever items change
  useEffect(() => {
    const itemCount = items.reduce((total, item) => total + item.quantity, 0);
    const price = items.reduce((total, item) => total + (item.item.price * item.quantity), 0);

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
  const addItem = (item: ItemResponse, newMerchantId: string, newMerchantName: string, merchantDeliveryFee?: number) => {
    // Check if the cart is empty
    if (items.length === 0) {
      // Cart is empty, set the merchant ID and name
      setMerchantId(newMerchantId);
      setMerchantName(newMerchantName);
    } else if (newMerchantId !== merchantId) {
      // Cart is not empty and item is from a different merchant
      setError(`Your cart already has items from ${ merchantName }. Please clear your cart before adding items from ${ newMerchantName }.`);
      return;
    }

    // Clear any previous errors
    clearError();

    setItems(prevItems => {
      // Check if item already exists in cart
      const existingItemIndex = prevItems.findIndex(cartItem => cartItem.item.id === item.id);

      if (existingItemIndex >= 0) {
        // Item exists, increment quantity
        const updatedItems = [...prevItems];
        updatedItems[existingItemIndex] = {
          ...updatedItems[existingItemIndex],
          quantity: updatedItems[existingItemIndex].quantity + 1,
        };
        return updatedItems;
      } else {
        // Item doesn't exist, add new item with quantity 1
        return [...prevItems, { item, quantity: 1 }];
      }
    });

    // Update merchant delivery fee if provided
    if (merchantDeliveryFee !== undefined) {
      setMerchantDeliveryFee(merchantDeliveryFee);
    }

    // Open the cart drawer whenever an item is added
    openCart();
  };

  const removeItem = (itemId: string) => {
    setItems(prevItems => prevItems.filter(item => item.item.id !== itemId));
  };

  const updateQuantity = (itemId: string, quantity: number) => {
    if (quantity <= 0) {
      removeItem(itemId);
      return;
    }

    setItems(prevItems =>
      prevItems.map(item =>
        item.item.id === itemId ? { ...item, quantity } : item
      )
    );
  };

  const clearCart = () => {
    setItems([]);
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
        updateQuantity,
        clearCart,
        totalItems,
        totalPrice,
        merchantDeliveryFee,
        merchantId,
        merchantName,
        error,
        clearError,
      } }
    >
      { children }
    </CartContext.Provider>
  );
};

export default CartContext;
