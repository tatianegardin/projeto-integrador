import React from 'react'
import { BookedVehicle } from '../components/ProductReservation'
import Navbar from '../components/Navbar'
import Footer from '../components/Footer'
import { ScrollRestoration, useLocation } from 'react-router-dom'

export default function Home() {

    const location = useLocation()
    localStorage.setItem('currentLocation', location.pathname)

    return (
        <>
            <ScrollRestoration />
            <Navbar />
            <BookedVehicle />
            <Footer />
        </>
    )
}