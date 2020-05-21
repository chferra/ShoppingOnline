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

//import { withNavigation } from 'react-navigation';

export default class LoginScreen extends Component {
    
    constructor(props){
        super(props);
        //this.Login = this.Login.bind(this);
    }

    state = {
        isLoggingIn: false,
        email: '',
        password: '',
        nav: this.props.navigator
    }

    Login = async (navigate) => {
        
        //this.setState({isLoggingIn: true});
        
        fetch('http://192.168.1.10:8080/ShoppingOnlineWS/resources/user?email=' + this.state.email + '&password=' + this.state.password, {
              method: 'get',
              headers: {
                'Accept': 'application/json'
              },
            }).then(function(response) {
                //Alert.alert(response.status.toString())
                //this.setState({isLoggingIn: false});
                //const navigation = useNavigation();
                if (response.status === 200) 
                    navigate('Home');

                //Alert.alert("ok"); //response.headers);

                
            }).catch((error)=>{
                console.log("Api call error");
                alert(error.message);
             });
          
    } 
    

    render() {
        const { navigate } = this.props.navigation;
        return (
            <View style={styles.main}>
                <ImageBackground source={loginBg} style={styles.image} />

                <View style={styles.container}>
                
                    <Text 
                        style={{fontSize: 27, color:'white', textAlign:'center'}}>
                        Login
                    </Text>
                    <View style={{marginTop:'25%'}} />
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
                    <Text style={{color: 'white', textAlign: 'right'}} onPress={() => navigate('ResetPassword') }  >
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

                </View>

                    <View style={{marginTop:'30%'}} />

                    <Text style={{textAlign: 'center', color:'white'}}>Don't have an account? 
                        <Text> </Text>
                        <Text style={{fontWeight: "bold", color:"white"}} onPress={() => navigate('Signup') }  >
                            Sign up now
                        </Text> 
                    </Text>                        
                    
                
                
            </View>
        )
    }
    
}

const loginBg = require("./images/loginBg.jpg");

const styles = StyleSheet.create({
    container: {
        marginTop: '40%',
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
        height: '17.5%',
        paddingLeft: 20,   
        color:'white',

        //marginTop: '5%'
        
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

