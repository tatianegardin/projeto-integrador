import React, { useEffect, useState } from 'react'
import styles from './Product.module.scss'
import Navbar from '../components/Navbar'
import Footer from '../components/Footer'
import CardProduct from '../components/CardProduct'
import { useParams } from 'react-router'

import { RotatingLines } from 'react-loader-spinner'

export default function Product() {
  const params = useParams()
  const [product, setProduct] = useState(null)

  useEffect(() => {
    async function fetchProduct() {
      const response = await fetch(`/product/all`)
      const products = await response.json()
      const product = products.find(p => p.id === parseInt(params.id))
      setProduct(product)
    }
    fetchProduct()
  }, [params])

  return (
    <div className={styles.appProduct}>
      <div className={styles.bodyProduct}>
        <Navbar />
        {product ? (
          <CardProduct key={product.id} imageData={product} />
        ) : (
          <div className={styles.spinner}>
            <RotatingLines
              strokeColor="#499167"
              strokeWidth="5"
              animationDuration="0.75"
              width="48"
              visible={true}
            />
          </div>
        )}
        <Footer />
      </div>
    </div>
  )
}
