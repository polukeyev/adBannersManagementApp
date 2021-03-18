import './App.css';
import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'

// import FooterComponent from './components/FooterComponent';
import HeaderComponent from './components/HeaderComponent';
import ListBannersComponent from './components/ListBannersComponent'
import ListCategoriesComponent from './components/ListCategoriesComponent'
import BannerTaskComponent from './components/BannerTaskComponent';

class App extends Component {
  render() {
  return (
      <Router>
        <div>
          <HeaderComponent/>
            <div className="container mt-3">
              <Switch>
                <Route exact path={["/", "/banners"]} component={ListBannersComponent}/>
                <Route path="/categories" component={ListCategoriesComponent}/>
                <Route path="/bid" component={BannerTaskComponent}/>
              </Switch>
            </div>
          {/* <FooterComponent/> */}
          </div>
      </Router>
  );
}
}

export default App;