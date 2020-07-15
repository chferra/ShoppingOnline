import React, { Component } from 'react';
import {

    Text,
    TextInput,
    View,
    StyleSheet,
    ImageBackground,
    Dimensions,
    TouchableHighlight,
    Alert
} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import DatePicker from 'react-native-datepicker';
//import { createStackNavigator, createAppContainer } from 'react-navigation';

export default class SignupScreen extends Component {
    state = {
        isSigningUp: false,
        name: '',
        email: '',
        password: '',     
        date: "2016-05-15",   
        nav: this.props.navigator
    }

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View style={styles.main}>
                <ImageBackground source={faidingBg} style={styles.image} />
                
                <Text style={{fontSize:27, color:'white', textAlign: 'center', marginTop: '25%'}}>Create your account</Text>
                <ScrollView style={styles.container}>
                    <View style={{marginTop:'5%'}} />
                    <TextInput placeholder='Name' style={styles.inputField} placeholderTextColor='white' 
                        onChangeText={(value) => this.setState({name: value})}
                        value={this.state.name}
                    />
                    <View style={{marginTop:'5%'}} />
                    <TextInput placeholder='Surname' style={styles.inputField} placeholderTextColor='white' 
                        onChangeText={(value) => this.setState({surname: value})}
                        value={this.state.surname}
                    />
                <View style={{marginTop:'5%'}} />
                <DatePicker
                    style={styles.datePickerStyle}
                    date={this.state.date}
                    mode="date"
                    placeholder="Birthdate"
                    format="YYYY-MM-DD"
                    minDate="2016-05-01"
                    maxDate="2016-06-01"
                    confirmBtnText="Confirm"
                    cancelBtnText="Cancel"
                    customStyles={{
                    dateIcon: {
                        position: 'absolute',
                        left: 0,
                        top: 4,
                        marginLeft: 0
                    },
                    dateInput: {                        
                        borderColor: 'transparent',
                        
                    }
                    // ... You can check the source to find the other keys.
                    }}
                    onDateChange={(date) => {this.setState({date: date})}}
                />
            
                    <View style={{marginTop:'5%'}} />
                    <TextInput placeholder='Email' style={styles.inputField} placeholderTextColor='white' 
                        onChangeText={(value) => this.setState({email: value})}
                        value={this.state.email}
                    />
                    
                    <View style={{marginTop:'5%'}} />
                    <TextInput placeholder='Password' style={styles.inputField} secureTextEntry={true} placeholderTextColor='white' 
                        onChangeText={(value) => this.setState({password: value})}
                        value={this.state.password}
                    />

                    <View style={{marginTop:'5%'}} />
                    <Text style={{color: 'white', marginLeft: '50%'}} onPress={() => navigate('ResetPassword') }  >
                        Forgot your password?
                    </Text>    
                    <View style={{marginTop:'10%'}} />                    
                            
                    <TouchableHighlight 
                        style={styles.button} 
                        //onPress={this.Login.bind(this)} 
                        onPress={() => this.Login(navigate)}
                        disabled={this.state.isLoggingIn||!this.state.email||!this.state.password} >
                        <Text style={styles.buttonText}>Log in</Text>
                    </TouchableHighlight>

                </ScrollView>
            
            </View>
        )
    }
    
}

const faidingBg = require("./images/fadingBg.jpg");

const styles = StyleSheet.create({
    container: {
        marginTop: '10%',
        marginLeft: '10%',
        marginRight: '10%',
        

        //marginTop: 100,
    },
    image: {
        flex: 1,
        width: Dimensions.get("window").width,
        height: Dimensions.get("window").height, 
    },
    inputField: {
        fontFamily: "sans-serif",
        fontSize: 16,
        borderColor: 'white',
        borderWidth: 0.5,
        borderRadius: 30,
        width: '100%',
        height: '16.5%',
        paddingLeft: 20,   
        color:'white',

        //marginTop: '5%'
        
    },
    datePickerStyle: {
        width: '100%',
        height: '17%',
        color:'white',
        borderColor: 'white',
        paddingLeft: 20, 
        borderWidth: 0.5,
        borderRadius: 30,
        paddingTop: 10
    },
    inputFieldText: {
        fontFamily: "sans-serif",
        color: 'white'
    },  
    button: {
        borderRadius: 30,
        backgroundColor: 'white',
        width: '100%',
        height: '17.5%',
    },
    buttonText: {
        textAlign:'center',
        paddingTop: '4%',
        color: '#f1491a',
        fontSize: 18,
        fontWeight: "bold"
    }
});

