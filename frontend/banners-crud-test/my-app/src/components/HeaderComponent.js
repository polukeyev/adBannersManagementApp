import React, { Component } from 'react';

class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {

        }
    }

    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <div className="navbar-brand">Banners management app</div>
                        <div className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <a href="/banners" className="nav-link">
                                    Banners
                                </a>
                            </li>
                            <li className="nav-item">
                                <a href="/categories" className="nav-link">
                                    Categories
                                </a>
                            </li>
                            <li className="nav-item">
                                <a href="/bid" className="nav-link">
                                    Task request page
                                </a>
                            </li>
                        </div>
                    </nav>
                </header>
                
            </div>
        );
    }
}

export default HeaderComponent;