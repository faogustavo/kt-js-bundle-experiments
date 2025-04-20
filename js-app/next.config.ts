import type { NextConfig } from 'next';
import { join } from 'path';

const nextConfig: NextConfig = {
  reactStrictMode: true,
  turbopack: {
    root: join(__dirname, '..'),
  },
  images: {
    remotePatterns: [
      { hostname: 'picsum.photos' },
    ],
  },
};

export default nextConfig;
