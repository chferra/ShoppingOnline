import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { createAppContainer } from "react-navigation";
import { createStackNavigator } from 'react-navigation-stack';

import LoginScreen from './components/screens/LoginScreen';
import HomeScreen from './components/screens/HomeScreen';
import SignupScreen from './components/screens/SignupScreen';
import SignupProfilePicScreen from './components/screens/SignupProfilePicScreen';
import ResetPasswordScreen from './components/screens/ResetPasswordScreen';

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
    screen: SignupScreen,
    navigationOptions: {
      title: '',
      headerTransparent: true, 
      headerTitleStyle: {
        color: 'white'
      },
      headerTintColor: 'white',      
    }
    
  },
  SignupProfilePic: {
    screen: SignupProfilePicScreen,
    navigationOptions: {
      title: '',
      headerTransparent: true, 
      headerTitleStyle: {
        color: 'white'
      },
      headerTintColor: 'white',      
    }
  },
  ResetPassword: {
    screen: ResetPasswordScreen
  }
},{
  initialRouteName: "Login",
});

const AppContainer = createAppContainer(AppNavigator);