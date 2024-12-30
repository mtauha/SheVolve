import React, { useEffect, useState } from "react";
import { useNavigate, Link, Navigate } from 'react-router-dom';
import { Button } from "../../components/ui/button";
import "../../styles/product-list.css";
import EntrepreneurNavBar from "./EntrepreneurNavBar";
import Footer from "../Footer";
import getJwtToken from "../../hooks/GetJwtToken";
import ProductCard from "../../components/card/ProductCard";
import EditProductForm from "./EditProductForm"; // Assuming you have an EditProductForm component

interface Product {
    productId: number;
    productName: string;
    productPrice: number;
    productImage: string; // Assuming base64-encoded image
    productDescription: string;
}

const ProductList: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const token = getJwtToken();
    const navigate = useNavigate();

    const fetchProducts = async () => {
        setLoading(true);
        try {
            const response = await fetch("http://localhost:8080/api/entrepreneur/products", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                setProducts(data); // Directly set products
            } else {
                console.error("Failed to fetch products");
            }
        } catch (error) {
            console.error("Error fetching products:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleEditProduct = (product: Product) => {
        setSelectedProduct(product);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setSelectedProduct(null);
    };

    const handleSaveProduct = async (updatedProduct: Product) => {
        try {
            const response = await fetch(`http://localhost:8080/api/products/${updatedProduct.productId}`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(updatedProduct),
            });
            if (response.ok) {
                setProducts(products.map(product =>
                    product.productId === updatedProduct.productId ? updatedProduct : product
                ));
                handleCloseModal();
            } else {
                console.error("Failed to save product");
            }
        } catch (error) {
            console.error("Error saving product:", error);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []); // Empty dependency array to fetch products once when the component mounts

    return (
        <>
            <EntrepreneurNavBar />
            <div className="product-list-page">
                <header className="product-list-header">
                    <h1>Products</h1>
                    <Button onClick={() => navigate('/entrepreneur/add-product')}>Add New Product</Button>
                </header>
                <div className="product-grid">
                    {loading ? (
                        <p>Loading...</p>
                    ) : (
                        products.map(product => (
                            <ProductCard
                                key={product.productId}
                                product={product} // Pass the full product
                                onEdit={() => handleEditProduct(product)}
                            />
                        ))
                    )}
                </div>
            </div>

            {isModalOpen && selectedProduct && (
                <EditProductForm
                    product={selectedProduct}
                    onClose={handleCloseModal}
                    onSave={handleSaveProduct}
                />
            )}

            <Footer />
        </>
    );
};

export default ProductList;
