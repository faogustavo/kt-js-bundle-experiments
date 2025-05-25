"use client";
import Image from 'next/image';
import Link from 'next/link';
import { MerchantResponse } from 'kt-js-experiment';

// Helper function to format price from cents to dollars
const formatPrice = (price: number): string => {
  return `$${(price / 100).toFixed(2)}`;
};

interface HomeListProps {
  merchants: Array<MerchantResponse> | undefined;
}

export default function HomeList({ merchants }: HomeListProps) {
  return (
    <div data-testid="merchant-list" className="flex flex-col space-y-4 max-w-3xl mx-auto">
      {merchants?.map((merchant) => (
        <Link
          key={merchant.id}
          href={`/merchant/${merchant.id}`}
          className="border rounded-lg overflow-hidden shadow-sm hover:shadow-md transition-shadow w-full flex flex-row cursor-pointer"
        >
          <div className="relative h-32 w-32 sm:h-40 sm:w-40 flex-shrink-0">
            <Image
              src={merchant.imageUrl}
              alt={merchant.name}
              fill
              style={{ objectFit: 'cover' }}
            />
            {!merchant.isOpen && (
              <div className="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 rounded text-sm">
                Closed
              </div>
            )}
          </div>

          <div className="p-4 flex-grow">
            <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-2">
              <h2 className="text-xl font-semibold mb-1 sm:mb-0">{merchant.name}</h2>
              <div className="flex items-center">
                <span className="text-yellow-500 mr-1">★</span>
                <span>{(merchant.rating / 10).toFixed(1)}</span>
                <span className="text-gray-400 text-sm ml-1">({merchant.ratingCount})</span>
              </div>
            </div>

            <p className="text-gray-600 dark:text-gray-400 text-sm mb-2">{merchant.category}</p>

            <div className="flex flex-wrap gap-1 sm:gap-2 text-sm text-gray-500 dark:text-gray-400 mb-2">
              <span>{merchant.address.city}</span>
              <span>•</span>
              <span>{merchant.deliveryTime} min</span>
              <span>•</span>
              <span>
                {merchant.deliveryFee === 0
                  ? 'Free Delivery'
                  : `Delivery ${formatPrice(merchant.deliveryFee)}`}
              </span>
            </div>

            {merchant.minimumOrder > 0 && (
              <div className="text-sm text-gray-500 dark:text-gray-400">
                Min. order: {formatPrice(merchant.minimumOrder)}
              </div>
            )}
          </div>
        </Link>
      ))}

      {merchants?.length === 0 && (
        <div className="text-center py-12">
          <p className="text-xl text-gray-500 dark:text-gray-400">No restaurants found</p>
        </div>
      )}
    </div>
  );
}
