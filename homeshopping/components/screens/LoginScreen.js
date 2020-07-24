import React, { Component } from 'react';
import {

    Text,
    TextInput,
    View,
    StyleSheet,
    ImageBackground,
    Dimensions,
    TouchableHighlight,
    Alert,
    Platform
} from 'react-native';
import Dialog from "react-native-dialog";
import { ScrollView } from 'react-native-gesture-handler';
import Icon from 'react-native-vector-icons/FontAwesome';

//import { withNavigation } from 'react-navigation';

export default class LoginScreen extends Component {

    constructor(props) {
        super(props);
        global.serverIP = ''
        //this.Login = this.Login.bind(this);
    }

    state = {
        isLoggingIn: false,
        email: '',
        password: '',
        nav: this.props.navigator,
        dialogVisible: true
    }

    Login = async (navigate) => {

        //this.setState({ isLoggingIn: true });

        fetch('http://' + global.serverIP + ':8080/ShoppingOnlineWS/resources/user?email=' + this.state.email + '&password=' + this.state.password, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
        }).then(function (response) {

            if (response.status === 200)
                navigate('Home');
            else
                Alert.alert("Error", "Invalid credentials");



        }).catch((error) => {
            console.log("Api call error");
            alert(error.message);
        });

    }

    handleOk = () => {
        this.setState({ dialogVisible: false });
    };



    render() {
        const { navigate } = this.props.navigation;
        return (
            <View>
                <Dialog.Container visible={this.state.dialogVisible}>
                    <Dialog.Title>Set server IP</Dialog.Title>
                    <Dialog.Input underlineColorAndroid={'gray'} label="IP" onChangeText={(ip) => global.serverIP = ip}>
                    </Dialog.Input>
                    <Dialog.Button label="OK" onPress={this.handleOk} />
                </Dialog.Container>

                <ImageBackground source={faidingBg} style={styles.image} />

                <ScrollView style={styles.container}>

                    <Text
                        style={{ fontSize: 27, color: 'white', textAlign: 'center', fontWeight: "bold" }}>
                        Welcome back
                    </Text>

                    <View style={{ marginTop: "10%" }} />

                    <View style={styles.inputContainer}>
                        <Icon color="white" name="envelope" size={30} style={styles.icon} />
                        <TextInput placeholder='Email' style={styles.inputField}
                            placeholderTextColor='white'

                            onChangeText={(value) => this.setState({ email: value })}
                            value={this.state.email}

                        />
                    </View>

                    <View style={styles.inputContainer}>
                        <Icon color="white" name="key" size={30} style={styles.icon} />
                        <TextInput placeholder='Password' style={styles.inputField}
                            placeholderTextColor='white'
                            secureTextEntry={true}
                            onChangeText={(value) => this.setState({ password: value })}
                            value={this.state.password}

                        />
                    </View>

                    <View style={{ marginTop: '5%' }} />
                    <Text style={{ color: 'white', marginLeft: '50%' }} onPress={() => navigate('ResetPassword')}  >
                        Forgot your password?
                    </Text>
                    <View style={{ marginTop: '10%' }} />

                    <TouchableHighlight
                        style={styles.button}
                        //onPress={this.Login.bind(this)}
                        onPress={() => this.Login(navigate)}
                        disabled={this.state.isLoggingIn || !this.state.email || !this.state.password} >
                        <Text style={styles.buttonText}>Log in</Text>
                    </TouchableHighlight>

                    <View style={{ marginTop: '30%' }} />



                </ScrollView>

                <View style={{ marginTop: '25%' }} />

                <Text style={{ textAlign: 'center', color: 'white' }}>Don't have an account? {""}
                    <Text style={{ fontWeight: "bold", color: "white" }} onPress={() => navigate('Signup')}  >
                        Sign up now
                        </Text>
                </Text>

            </View>
        )
    }

}

const faidingBg = require("./images/fadingBg.jpg");

const styles = StyleSheet.create({
    container: {
        marginTop: '50%',
        marginLeft: '10%',
        marginRight: '10%'
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
        height: '13%',
    },
    buttonText: {
        textAlign: 'center',
        paddingTop: '4%',
        color: '#f1491a',
        fontSize: 18,
        fontWeight: "bold"
    },
    inputContainer: {
        flexDirection: 'row',
        borderBottomWidth: 1,
        paddingBottom: 10,
        borderColor: 'white',
        borderWidth: 0.5,
        borderRadius: 30,
        width: '100%',
        height: '13%',
        paddingLeft: 20,
        marginTop: '5%'
    },
    icon: {
        marginTop: "3%",
        height: "100%",
        width: "15%"
    }
});
