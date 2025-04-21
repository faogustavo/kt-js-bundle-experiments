"use client";
import { useParams } from 'next/navigation';
import { useQuery } from '@tanstack/react-query';
import { ItemResponse, MerchantResponse, MerchantService } from 'kt-js-experiment';
import Image from 'next/image';
import Link from 'next/link';
import { useCart } from '@/context/CartContext';
import { useState } from 'react';
import MenuItemPopup from '@/components/MenuItemPopup';

// Helper function to format price from cents to dollars
const formatPrice = (price: number): string => {
  return `$${(price / 100).toFixed(2)}`;
};

// MenuItem component to display a menu item
const MenuItem = ({
                    item,
                    merchantId,
                    merchantName,
                    merchantDeliveryFee,
                    merchantCategory,
                    merchantDeliveryTime,
                  }: {
  item: ItemResponse,
  merchantId: string,
  merchantName: string,
  merchantDeliveryFee: number,
  merchantCategory: string,
  merchantDeliveryTime: number
}) => {
  const { addItem } = useCart();
  const [showQuantityPopup, setShowQuantityPopup] = useState(false);

  const handleAddToCart = () => {
    setShowQuantityPopup(true);
  };

  const handleConfirmAddToCart = (
    item: ItemResponse,
    _cartItemId: number | undefined,
    merchantId: string,
    merchantName: string,
    quantity: number,
    selectedOptions: Record<string, boolean | string | string[] | null>,
    observation: string,
    merchantDeliveryFee: number,
    merchantCategory?: string,
    merchantDeliveryTime?: number,
  ) => {
    addItem(item, merchantId, merchantName, quantity, selectedOptions, observation, merchantDeliveryFee, merchantCategory, merchantDeliveryTime);
    setShowQuantityPopup(false);
  };

  const handleCancelAddToCart = () => {
    setShowQuantityPopup(false);
  };

  return (
    <div
      className={ `border rounded-lg p-4 transition-all duration-200 flex ${
        item.isAvailable
          ? 'hover:shadow-lg hover:bg-gray-50 dark:hover:bg-gray-800 hover:border-blue-300 cursor-pointer'
          : 'opacity-75'
      }` }
      onClick={ item.isAvailable ? handleAddToCart : undefined }
    >
      <div className="relative h-20 w-20 min-w-20 mr-4">
        <Image
          src={item.imageUrl}
          alt={item.name}
          fill
          style={{ objectFit: 'cover' }}
          className="rounded-md"
        />
      </div>
      <div className="flex-1">
        <div className="flex justify-between mb-2">
          <h4 className="font-semibold">{item.name}</h4>
          <span>{formatPrice(item.price)}</span>
        </div>
        <p className="text-sm text-gray-600 dark:text-gray-400">{item.description}</p>
        {!item.isAvailable && (
          <p className="text-red-500 text-sm mt-2">Currently unavailable</p>
        )}
      </div>

      { showQuantityPopup && (
        <MenuItemPopup
          item={ item }
          merchantId={ merchantId }
          merchantName={ merchantName }
          merchantDeliveryFee={ merchantDeliveryFee }
          merchantCategory={ merchantCategory }
          merchantDeliveryTime={ merchantDeliveryTime }
          onConfirm={ handleConfirmAddToCart }
          onCancel={ handleCancelAddToCart }
        />
      ) }
    </div>
  );
};

// Function to fetch a single merchant using MerchantService
const fetchMerchant = async (id: string): Promise<MerchantResponse | null | undefined> => {
  return MerchantService.getInstance().getMerchantById(id);
};

export default function MerchantDetails() {
  const params = useParams();
  const merchantId = params!.id as string;

  // Use React Query to fetch merchant details
  const { data: merchant, isLoading, error } = useQuery({
    queryKey: ['merchant', merchantId],
    queryFn: () => fetchMerchant(merchantId)
  });

  // Show loading state
  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl font-semibold">Loading restaurant details...</div>
      </div>
    );
  }

  // Show error state
  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl font-semibold text-red-500">
          Error loading restaurant details: {error.toString()}
        </div>
      </div>
    );
  }

  // Show not found state
  if (!merchant) {
    return (
      <div className="min-h-screen flex items-center justify-center flex-col">
        <div className="text-xl font-semibold text-red-500 mb-4">
          Restaurant not found
        </div>
        <Link href="/" className="text-blue-500 hover:underline">
          Back to restaurants
        </Link>
      </div>
    );
  }

  return (
    <div className="min-h-screen px-4 sm:px-8 font-[family-name:var(--font-geist-sans)]">
      <div className="max-w-4xl mx-auto">
        <div className="relative h-48 sm:h-64 md:h-80 w-full mb-6">
          <Image
            src={merchant.imageUrl}
            alt={merchant.name}
            fill
            style={{ objectFit: 'cover' }}
            className="rounded-lg"
          />
          {!merchant.isOpen && (
            <div className="absolute top-4 right-4 bg-red-500 text-white px-3 py-1 rounded text-sm">
              Closed
            </div>
          )}
        </div>

        <div className="mb-6">
          <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-2">
            <h1 className="text-3xl font-bold mb-2 sm:mb-0">{merchant.name}</h1>
            <div className="flex items-center">
              <span className="text-yellow-500 mr-1">★</span>
              <span>{(merchant.rating / 10).toFixed(1)}</span>
              <span className="text-gray-400 text-sm ml-1">({merchant.ratingCount})</span>
            </div>
          </div>

          <p className="text-gray-600 dark:text-gray-400 text-lg mb-2">{merchant.category}</p>

          <div className="flex flex-wrap gap-2 text-gray-500 dark:text-gray-400 mb-4">
            <span>{merchant.address.city}</span>
            <span>•</span>
            <span>{merchant.deliveryTime} min</span>
            <span>•</span>
            <span>
              {merchant.deliveryFee === 0
                ? 'Free Delivery'
                : `Delivery ${formatPrice(merchant.deliveryFee)}`}
            </span>
            {merchant.minimumOrder > 0 && (
              <>
                <span>•</span>
                <span>Min. order: {formatPrice(merchant.minimumOrder)}</span>
              </>
            )}
          </div>

          <div className="mb-4">
            <h2 className="text-xl font-semibold mb-2">Address</h2>
            <p>{merchant.address.addressLine1}</p>
            {merchant.address.addressLine2 && <p>{merchant.address.addressLine2}</p>}
            <p>{merchant.address.city}, {merchant.address.state} {merchant.address.zip}</p>
          </div>

          <div>
            <h2 className="text-xl font-semibold mb-2">Contact</h2>
            <p>{merchant.phoneNumber}</p>
          </div>
        </div>

        <div>
          <h2 className="text-2xl font-bold mb-4">Menu</h2>
          {merchant.menu.map(category => (
            <div key={category.id} className="mb-8">
              <h3 className="text-xl font-semibold mb-3">{category.name}</h3>
              {category.description && (
                <p className="text-gray-600 dark:text-gray-400 mb-4">{category.description}</p>
              )}

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {category.items.map(item => (
                  <MenuItem
                    key={ item.id }
                    item={ item }
                    merchantId={ merchantId }
                    merchantName={ merchant.name }
                    merchantDeliveryFee={ merchant.deliveryFee }
                    merchantCategory={ merchant.category }
                    merchantDeliveryTime={ merchant.deliveryTime }
                  />
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
