import React, { useState } from 'react';
import { Button } from '../../components/ui/button';
import { Input } from '../../components/ui/input';

interface Product {
    productId: number;
    productName: string;
    productPrice: number;
    productImage: string;
    productDescription: string;
}

interface EditProductFormProps {
    product: Product;
    onSave: (updatedProduct: Partial<Product>) => void;
    onCancel: () => void;
}

export const EditProductForm: React.FC<EditProductFormProps> = ({
    product,
    onSave,
    onCancel,
}) => {
    const [formData, setFormData] = useState({
        productName: product.productName,
        productPrice: product.productPrice,
        productDescription: product.productDescription,
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSave(formData);
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-card-bg p-6 rounded-lg shadow-lg w-full max-w-md">
                <h2 className="text-2xl font-bold text-primary-color mb-4">Edit Product</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <Input
                        label="Product Name"
                        name="productName"
                        value={formData.productName}
                        onChange={handleChange}
                    />
                    <Input
                        label="Product Price"
                        name="productPrice"
                        type="number"
                        value={formData.productPrice}
                        onChange={handleChange}
                    />
                    <div className="space-y-2">
                        <label htmlFor="productDescription" className="block text-text-color">
                            Product Description
                        </label>
                        <textarea
                            id="productDescription"
                            name="productDescription"
                            value={formData.productDescription}
                            onChange={handleChange}
                            className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-color"
                            rows={3}
                        />
                    </div>
                    <div className="flex justify-end space-x-2">
                        <Button onClick={onCancel} variant="secondary">
                            Cancel
                        </Button>
                        <Button type="submit">Save</Button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditProductForm;