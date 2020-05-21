import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { createAppContainer } from "react-navigation";
import { createStackNavigator } from 'react-navigation-stack';

import LoginScreen from './components/screens/LoginScreen';
import HomeScreen from './components/screens/HomeScreen';
import SignupScreen from './components/screens/SignupScreen';

export default class App extends Component {

  render() {
    return <AppContainer />;
  }

}

const AppNavigator = createStackNavigator({
  Login: {
    screen: LoginScreen,
    navigationOptions: {
      headerShown: false
   }
  },
  Home: {
    screen: HomeScreen
  },
  Signup: {
    screen: SignupScreen
  }
},{
  initialRouteName: "Login",
});

const AppContainer = createAppContainer(AppNavigator);