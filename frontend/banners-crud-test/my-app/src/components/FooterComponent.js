import React, { Component } from 'react';

class FooterComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {

        }
    }

    render() {
        return (
            <div>
                <footer className="navbar fixed-bottom avbar-expand-md navbar-dark bg-dark">
                    <span className="text-muted">Designed by Oleg Polukeyev in 2021 @polukeyev</span>
                </footer>
            </div>
        );
    }
}

export default FooterComponent;