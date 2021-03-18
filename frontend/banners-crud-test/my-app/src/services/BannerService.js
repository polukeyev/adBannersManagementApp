import axios from 'axios';

const BANNERS_API_URL = "http://localhost:8080/banners";
const BANNER_TASK_URL = "http://localhost:8080/bid";

class BannerService {
    
    getBanners() {
        return axios.get(BANNERS_API_URL);
    }

    createBanner(banner) {
        return axios.post(BANNERS_API_URL, banner);
    }

    getBannerById(bannerId) {
        return axios.get(BANNERS_API_URL + '/' + bannerId);
    }

    updateBanner(bannerId, banner) {
        return axios.put(BANNERS_API_URL + '/' + bannerId, banner);
    }

    deleteBanner(bannerId) { 
        return axios.delete(BANNERS_API_URL + '/' + bannerId);
    }

    findBannerByName(name) {
        return axios.get(BANNERS_API_URL + '?name=' + name);
    }

    getBannerByCategoryReq(categoryReq) {
        return axios.get(BANNER_TASK_URL + '?category=' + categoryReq)
    }
}

export default new BannerService()