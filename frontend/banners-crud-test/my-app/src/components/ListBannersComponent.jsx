import React, { Component } from 'react';
import BannerService from '../services/BannerService';
import CategoryService from '../services/CategoryService';

class ListBannersComponent extends Component {
    constructor(props) {
        super(props)
        this.onChangeSearchName = this.onChangeSearchName.bind(this)
        this.onChangeName = this.onChangeName.bind(this)
        this.onChangePrice = this.onChangePrice.bind(this)
        this.onChangeCategory = this.onChangeCategory.bind(this)
        this.onChangeContent = this.onChangeContent.bind(this)

        this.setActiveBanner = this.setActiveBanner.bind(this)
        this.searchName = this.searchName.bind(this) 
        
        this.setEditBanner = this.setEditBanner.bind(this)
        this.setCreateBanner = this.setCreateBanner.bind(this)

        this.getBanners = this.getBanners.bind(this)
        this.updateBanner = this.updateBanner.bind(this)
        this.createBanner = this.createBanner.bind(this)
        this.deleteBanner = this.deleteBanner.bind(this)

        this.cancelButton = this.cancelButton.bind(this)

        this.state = {
            banners: [],
            currentBanner: null,
            currentId: -1,
            searchName: "",
            
            editing: false,
            creating: false,
            onError: false,
            errorBody: [],

            categories: [],

            editingBanner: {
                name: "",
                price: 0,
                category: null,
                content: ""
            }
        }
    }

    componentDidMount() {
        this.getBanners()

        CategoryService.getCategories()
        .then( res => {
            this.setState({categories: res.data})
        })
        .catch(e => {
            console.log(e)
        })
    }

    onChangeSearchName(e) {
        const searchName = e.target.value;

        this.setState({
            searchName: searchName
        });
    }

    searchName() {
        BannerService.findBannerByName(this.state.searchName)
        .then( res => {
            this.setState({
                banners: res.data
            });
        })
    }

    getBanners() {
        BannerService.getBanners()
        .then(res => {
            this.setState({
                banners : res.data,
                onError: false,
                errorBody: []
            })
        })
        .catch(e => {
            console.log(e);
        })
    }

/////////////////////////////////////////////////

    createBanner() {
        BannerService.createBanner(this.state.editingBanner)
        .then( res => {
            this.setActiveBanner(res.data, res.data.id)
            this.setState({
                creating: false,
                editingBanner: {
                    name: "",
                    price: 0,
                    category: null,
                    content: ""
                }
            })
            this.getBanners()
        })
        .catch(e => {
            this.errorHandle(e)
        })
    }

    updateBanner() {
        BannerService.updateBanner(
            this.state.editingBanner.id,
            this.state.editingBanner
        )
        .then( res => {
            this.setActiveBanner(res.data, res.data.id)
            this.setState({
                editing: false,
                editingBanner: {
                    name: "",
                    price: 0,
                    category: null,
                    content: ""
                }
            })
            this.getBanners()
        })
        .catch(e => {
            this.errorHandle(e)
        })
    }

    deleteBanner() {
        BannerService.deleteBanner(this.state.currentBanner.id)
        .then(() => {
            this.setState({
                currentBanner: null,
                currentId: -1,
            })
            this.getBanners()
        })
        .catch(e => {
            this.errorHandle(e)
        })
    }

////////////////////////////////////////////////

    errorHandle(e) {
        this.setState({
            onError: true
        })
        if (e.response && e.response.data) {
            this.setState({
                errorBody : e.response.data.violations
            })}
    }

    setCreateBanner() {
        this.setState({
            creating: true,
            editing: false,
        });
    }

    setEditBanner() {
        this.setState(res => ({
            editing: true,
            creating: false,
            editingBanner: {
                ...res.currentBanner
            }
        }));
    }

    setActiveBanner(banner, index) {
        this.setState({
            currentBanner: banner,
            currentId: index,
            editing: false,
            creating: false
        });
    }

////////////////////////////////////////////////

    cancelButton() {
        this.setState({
            editing: false,
            creating: false,
            onError: false,
            errorBody: []
        })
    }

    onChangeName(e) {
        const name = e.target.value;

        this.setState( res => ({
            editingBanner: {
                ...res.editingBanner,
                name: name
            }
        }))
    }

    onChangePrice(e) {
        const price = e.target.value;

        this.setState( res => ({
            editingBanner: {
                ...res.editingBanner,
                price: price
            }
        }))
    }

    onChangeCategory(e) {
        const category = e.target.value;

        this.setState( res => ({
            editingBanner: {
                ...res.editingBanner,
                category: {
                    name: category
                }
            }
        }))
    }

    onChangeContent(e) {
        const content = e.target.value;

        this.setState( res => ({
            editingBanner: {
                ...res.editingBanner,
                content: content
            }
        }))
    }

/////////////////////////////////////////////////////////
    render() {
        const {searchName, banners, currentBanner, currentId, editing, creating, categories, editingBanner, errorBody} = this.state;

        return (
            <div className="lis row">
                <div className="col-sd-6">
                    <h4>
                        Banners List
                        {" "}
                        <button className="btn btn-info" onClick={() => this.setCreateBanner()}>Create banner</button>
                    </h4>
                    <div className="col-sd-8">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Filter by name"
                            value={searchName}
                            onChange={this.onChangeSearchName}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-info"
                                type="button"
                                onClick={this.searchName}
                                // disabled={searchName.length<3}
                            >Filter</button>
                        </div>
                    </div>
                </div>
                    <ul className="list-group">
                        {banners && banners.map((banner, _index) => (
                            <li
                            className={"list-group-item list-group-item-action" + (banner.id === currentId ? " active" : "")}
                            onClick={() => this.setActiveBanner(banner, banner.id)}
                            key={banner.id}
                            >
                                {banner.name}
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="col-md-6">
                        {creating ? (
                            <div className="edit-form">
                                <h4 align="center">Create new banner</h4>
                                <form>
                                    <div className="form-group">
                                        <label htmlFor="name">Name:</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            id="name"
                                            placeholder="Enter banner name"
                                            onChange={this.onChangeName}    
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="price">Price:</label>
                                        <input
                                            type="number"
                                            className="form-control"
                                            id="price"
                                            placeholder="Enter banner price"
                                            onChange={this.onChangePrice}    
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="category">Category:</label>
                                        <div>
                                            <select onChange={this.onChangeCategory}>
                                                <option value="" disabled selected hidden>Choose category...</option>
                                                {categories && categories.map((category) => (
                                                    <option key={category.id}>{category.name}</option>
                                                ))}
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="content">Content:</label>
                                        <textarea
                                            rows="2"
                                            type="text"
                                            className="form-control"
                                            id="content"
                                            placeholder="Enter banner content"
                                            onChange={this.onChangeContent}    
                                        />
                                    </div>
                                </form>
                                <div>
                                    <button className="btn btn-success" onClick={() => this.createBanner(currentBanner)}>Create</button>
                                        {" "}
                                    <button className="btn btn-info" onClick={() => this.cancelButton()}>Cancel</button>
                                </div>
                            </div>
                        ) : (
                            <div>
                                    {currentBanner ? (
                                        <div> 
                                            {editing ? (
                                                <div className="edit-form">
                                                    <h4 align="center"> Banner, id: {currentBanner.id} editing</h4>
                                                    <form>
                                                        <div className="form-group">
                                                            <label htmlFor="name">Name:</label>
                                                            <input
                                                                type="text"
                                                                className="form-control"
                                                                id="name"
                                                                value={editingBanner.name}
                                                                onChange={this.onChangeName}    
                                                            />
                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="price">Price:</label>
                                                            <input
                                                                type="number"
                                                                className="form-control"
                                                                id="price"
                                                                value={editingBanner.price}
                                                                onChange={this.onChangePrice}    
                                                            />
                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="category">Category:</label>
                                                            <div>
                                                                <select value={editingBanner.category.name} onChange={this.onChangeCategory}>
                                                                    {categories && categories.map((category) => (
                                                                        <option key={category.id}>{category.name}</option>
                                                                    ))}
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div className="form-group">
                                                            <label htmlFor="content">Content:</label>
                                                            <textarea
                                                                rows="2"
                                                                type="text"
                                                                className="form-control"
                                                                id="content"
                                                                value={editingBanner.content}
                                                                onChange={this.onChangeContent}    
                                                            />
                                                        </div>
                                                    </form>
                                                    <div>
                                                        <button className="btn btn-success" onClick={() => this.updateBanner()}>Save</button>
                                                            {" "}
                                                        <button className="btn btn-info" onClick={() => this.cancelButton()}>Cancel</button>
                                                    </div>
                                                </div>
                                                
                                                    ) : (
                                                        <div>
                                                            <h4 align="center">Banner description</h4>
                                                            <div>
                                                                <label>
                                                                    <strong>Name:</strong>
                                                                </label>{" "}
                                                                {currentBanner.name}
                                                            </div>
                                                            <div>
                                                                <label>
                                                                    <strong>Price:</strong>
                                                                </label>{" "}
                                                                {currentBanner.price}
                                                            </div>
                                                            <div>
                                                                <label>
                                                                    <strong>Category:</strong>
                                                                </label>{" "}
                                                                {currentBanner.category.name}
                                                            </div>
                                                            <div>
                                                                <label>
                                                                    <strong>Text:</strong>
                                                                </label>{" "}
                                                                {currentBanner.content}
                                                            </div>

                                                            <div>
                                                                <button className="btn btn-info" onClick={() => this.setEditBanner()}>Edit</button>
                                                                    {" "}
                                                                <button className="btn btn-danger" onClick={() => this.deleteBanner()}>Delete</button>
                                                            </div>

                                                        </div>
                                                    )}
                                                </div>
                                    ) : (
                                        <div>
                                            <br/>
                                            <br/>
                                            <p align="center">Please, click on the banner or create new</p>
                                        </div>
                                    )}
                                </div>
                    )}
                </div>
                <div className = {"container d-" + (this.state.onError ? "block" : "none")} role="alert">
                    <br/>
                    {errorBody && errorBody.map((error) => (
                        <div key={error.name} className="alert alert-danger">
                                {error.message}
                        </div>
                    ))}
                </div>
            </div>
        );
    }
}

export default ListBannersComponent