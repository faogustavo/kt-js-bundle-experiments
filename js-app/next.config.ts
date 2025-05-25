import type { NextConfig } from 'next';
import { join } from 'path';
import withBundleAnalyzer from '@next/bundle-analyzer';

const nextConfig: NextConfig = {
  reactStrictMode: true,
  productionBrowserSourceMaps: true,
  turbopack: {
    root: join(__dirname, '..'),
  },
  images: {
    remotePatterns: [
      { hostname: 'picsum.photos' },
    ],
  },
};

export default withBundleAnalyzer({
  enabled: process.env.ANALYZE === 'true',
})(nextConfig);
