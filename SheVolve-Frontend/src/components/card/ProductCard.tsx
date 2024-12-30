import React, { useState } from 'react';
import styles from '../../styles/product.module.css';

interface Product {
    productId: number;
    productName: string;
    productPrice: number;
    productImage: string; // Assuming base64 image data
    productDescription: string;
}

interface ProductCardProps {
    product: Product;
    onEdit: (product: Product) => void; // Pass full product when editing
}

export const ProductCard: React.FC<ProductCardProps> = ({ product, onEdit }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editedProduct, setEditedProduct] = useState(product);

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = () => {
        onEdit(editedProduct); // Pass edited product to the parent
        setIsEditing(false);
    };

    const handleCancel = () => {
        setEditedProduct(product); // Reset to the original product
        setIsEditing(false);
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setEditedProduct((prev) => ({ ...prev, [name]: value }));
    };

    return (
        <div className={styles.card}>
            <img src={`data:image/*;base64,${product.productImage}`} alt={product.productName} className={styles.image} />
            <h2 className={styles.name}>{product.productName}</h2>
            <p className={styles.description}>{product.productDescription}</p>
            <p className={styles.price}>${product.productPrice.toFixed(2)}</p>
            <button className={styles.editButton} onClick={handleEdit}>
                Edit
            </button>

            {isEditing && (
                <div className={styles.editForm}>
                    <div className={styles.formContent}>
                        <h3 className={styles.formTitle}>Edit Product</h3>
                        <div className={styles.formField}>
                            <label className={styles.formLabel} htmlFor="productName">
                                Product Name
                            </label>
                            <input
                                className={styles.formInput}
                                type="text"
                                id="productName"
                                name="productName"
                                value={editedProduct.productName}
                                onChange={handleChange}
                            />
                        </div>
                        <div className={styles.formField}>
                            <label className={styles.formLabel} htmlFor="productPrice">
                                Product Price
                            </label>
                            <input
                                className={styles.formInput}
                                type="number"
                                id="productPrice"
                                name="productPrice"
                                value={editedProduct.productPrice}
                                onChange={handleChange}
                            />
                        </div>
                        <div className={styles.formField}>
                            <label className={styles.formLabel} htmlFor="productDescription">
                                Product Description
                            </label>
                            <textarea
                                className={styles.formTextarea}
                                id="productDescription"
                                name="productDescription"
                                value={editedProduct.productDescription}
                                onChange={handleChange}
                            />
                        </div>
                        <div className={styles.formActions}>
                            <button className={styles.cancelButton} onClick={handleCancel}>
                                Cancel
                            </button>
                            <button className={styles.saveButton} onClick={handleSave}>
                                Save
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ProductCard;
