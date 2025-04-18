'use client';
import React, { createContext, useEffect } from 'react'
import { SharedKoin } from 'kt-js-experiment'

const KoinContext = createContext({})

export default function KoinContextProvider({ children }: { children: React.ReactNode }) {
  const koinContext = {
    // Define your context values here
  }

  useEffect(() => {
    SharedKoin.getInstance().initKoin()
  }, []);

  return (
    <KoinContext.Provider value={ koinContext }>
      { children }
    </KoinContext.Provider>
  )
}
