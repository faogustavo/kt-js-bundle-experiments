'use client';
import React from 'react';
import Link from 'next/link';
import { useCart } from '@/context/CartContext';

// Helper function to format price from cents to dollars
const formatPrice = (price: number): string => {
  return `$${ (price / 100).toFixed(2) }`;
};

const Header: React.FC = () => {
  const { toggleCart, totalPrice } = useCart();

  return (
    <header className="sticky top-0 z-10 bg-white dark:bg-gray-900 shadow-sm py-4 px-4 sm:px-8">
      <div className="max-w-7xl mx-auto flex justify-between items-center">
        <Link href="/" className="text-xl font-bold">
          Food Delivery
        </Link>

        <button
          onClick={ toggleCart }
          className="relative p-2 text-gray-700 hover:text-gray-900 dark:text-gray-300 dark:hover:text-white"
          aria-label="Shopping cart"
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
              d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"
            />
          </svg>

          { totalPrice > 0 && (
            <span
              className="absolute top-0 right-0 inline-flex items-center justify-center px-2 py-1 text-xs font-bold leading-none text-white transform translate-x-1/2 -translate-y-1/2 bg-red-500 rounded-full">
              { formatPrice(totalPrice) }
            </span>
          ) }
        </button>
      </div>
    </header>
  );
};

export default Header;
