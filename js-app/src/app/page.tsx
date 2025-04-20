"use client";
import { useQuery } from '@tanstack/react-query';
import { MerchantResponse, MerchantService } from 'kt-js-experiment';
import HomeList from '@/app/home/list';

// Function to fetch merchants using MerchantService
const fetchMerchants = async (): Promise<Array<MerchantResponse>> => {
  return MerchantService.getInstance().getAllMerchants();
};

export default function Home() {
  // Use React Query to fetch merchants
  const { data: merchants, isLoading, error } = useQuery({
    queryKey: ['merchants'],
    queryFn: fetchMerchants
  });

  // Show loading state
  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl font-semibold">Loading restaurants...</div>
      </div>
    );
  }

  // Show error state
  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-xl font-semibold text-red-500">
          Error loading restaurants: {error.toString()}
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen p-4 sm:p-8 font-[family-name:var(--font-geist-sans)]">
      <header className="mb-8">
        <h1 className="text-3xl font-bold mb-2">Restaurants</h1>
        <p className="text-gray-600 dark:text-gray-400">
          Find your favorite food from our selection of restaurants
        </p>
      </header>

      <main>
        <HomeList merchants={merchants} />
      </main>
    </div>
  );
}
