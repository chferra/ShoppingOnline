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
import Icon from 'react-native-vector-icons/FontAwesome';
//import { createStackNavigator, createAppContainer } from 'react-navigation';

export default class SignupScreen extends Component {

    state = {
        isSigningUp: false,
        name: '',
        surname: '',
        date: false,
        email: '',
        password: '',
        confirmPassword: '',
        nav: this.props.navigator
    }

    Signup = async (navigate) => {

        //this.setState({isSigningUp: true});

        fetch('http://' + global.serverIP + ':8080/ShoppingOnlineWS/resources/user', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: this.state.name,
                surname: this.state.surname,
                birthDate: this.state.date,
                email: this.state.email,
                password: this.state.password
            })
        }).then(function (response) {
            //this.setState({isSigningUp: false});

            if (response.status === 200)
                navigate('Home');
            else
                Alert.alert("Error", "The information you entered is invalid: " + response.status);

        }).catch((error) => {
            console.log("Api call error");
            alert(error.message);
        });

    }

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View>

                <ImageBackground source={faidingBg} style={styles.image} />

                <View style={styles.container}>
                    <Text style={{ fontSize: 27, fontWeight: "bold", color: 'white', textAlign: 'center' }}>Create your account</Text>
                    <Text style={{ fontSize: 18, color: '#ffb829', textAlign: 'center' }}>Shipping address</Text>
                    <View style={{ marginTop: '10%' }} />

                    <TouchableHighlight
                        style={styles.button}
                        onPress={() => /*this.Signup(navigate) */ navigate('SignupProfilePic')}
                    //disabled={this.state.isSigningUp || !this.state.name || !this.state.surname || !this.state.date || !this.state.email ||
                    //    !this.state.password || !this.state.confirmPassword} 
                    >
                        
                        <Text style={styles.buttonText}>Add a new address</Text>
                    </TouchableHighlight>
                </View>

                <View style={{ marginTop: '5%' }} />
                <Text>Your addresses:</Text>



                <ScrollView style={styles.container} >


                    <View style={{ marginTop: '5%' }} />


                    <View style={{ marginTop: '10%' }} />
                </ScrollView>
            </View>
        )
    }

}

const faidingBg = require("./images/fadingBg.jpg");

const styles = StyleSheet.create({
    container: {
        marginTop: '27%',
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
        color: 'white',
        flex: 1,
        marginLeft: "2%",
        marginTop: "2.5%"

    },
    inputFieldText: {
        fontFamily: "sans-serif",
        color: 'white'
    },
    button: {
        borderRadius: 30,
        backgroundColor: 'white',
        width: '100%',
        height: '9%',
    },
    buttonText: {
        textAlign: 'center',
        paddingTop: '4%',
        color: '#f1491a',
        fontSize: 18,
        fontWeight: "bold"
    },
    datePickerStyle: {
        width: '100%',
        height: '9.5%',
        color: 'white',
        borderColor: 'white',
        paddingLeft: 20,
        borderWidth: 0.5,
        borderRadius: 30,
        paddingTop: 10
    },
    inputContainer: {
        flexDirection: 'row',
        borderBottomWidth: 1,
        paddingBottom: 10,
        borderColor: 'white',
        borderWidth: 0.5,
        borderRadius: 30,
        width: '100%',
        height: '9%',
        paddingLeft: 20,
        marginTop: '5%'
    },
    icon: {
        marginTop: "3.5%",
        height: "100%",
        width: "15%"
    }
});

