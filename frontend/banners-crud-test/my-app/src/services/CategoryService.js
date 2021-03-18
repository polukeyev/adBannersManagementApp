import axios from 'axios';

const CATEGORIES_API_URL = "http://localhost:8080/categories";

class CategoryService {
    
    getCategories() {
        return axios.get(CATEGORIES_API_URL);
    }

    createCategory(category) {
        return axios.post(CATEGORIES_API_URL, category);
    }

    getCategoryById(categoryId) {
        return axios.get(CATEGORIES_API_URL + '/' + categoryId);
    }

    updateCategory(categoryId, category) {
        return axios.put(CATEGORIES_API_URL + '/' + categoryId, category);
    }

    deleteCategory(categoryId) {
        return axios.delete(CATEGORIES_API_URL + '/' + categoryId);
    }

    findCategoryByName(name) {
        return axios.get(CATEGORIES_API_URL + '?name=' + name);
    }
}

export default new CategoryService()