import type { NextConfig } from "next";
import { join } from 'path';

const nextConfig: NextConfig = {
  reactStrictMode: true,
  turbopack: {
    root: join(__dirname, ".."),
  },
};

export default nextConfig;
