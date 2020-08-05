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
    FlatList,
    ActivityIndicator,
    ListItem
} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { RadioButton } from 'react-native-paper';
//import { createStackNavigator, createAppContainer } from 'react-navigation';

export default class SignupScreen extends Component {

    state = {
        isSigningUp: false,
        nav: this.props.navigator,
        data: [],
        isLoading: true,
        value: 1
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

    componentDidMount() {
        fetch('http://' + global.serverIP + ':8080/ShoppingOnlineWS/resources/address')
            .then((response) => response.json())
            .then((json) => {
                this.setState({ data: json });
            })
            .catch((error) => console.error(error))
            .finally(() => {
                this.setState({ isLoading: false });
            });
    }

    checkRadioButton = (primaryAddress, ID) => {
        if (!primaryAddress) {
            Alert.alert(
                "Confirm",
                "Are you sure you want to make this the default shipping address?",
                [
                    {
                        text: "Cancel",
                        style: "cancel"
                    },
                    {
                        text: "OK", onPress: () =>
                            fetch('http://' + global.serverIP + ':8080/ShoppingOnlineWS/resources/address', {
                                method: 'PUT',
                                headers: {
                                    'Content-Type': 'text/plain'
                                },
                                body:
                                    ID.toString()
                            }).then(function (response) {
                                if (response.status === 204)
                                    this.componentDidMount();
                                else
                                    Alert.alert("Error", "Something went wrong with updating your default address. Try again");

                            }).catch((error) => {
                                console.log("Api call error");
                                alert(error.message);
                            })
                    }
                ],
                { cancelable: false }
            );
        }
    }

    render() {
        const { navigate } = this.props.navigation;
        const { data, isLoading } = this.state;

        return (
            <View>

                <ImageBackground source={faidingBg} style={styles.image} />

                <View style={styles.upperContainer}>
                    <Text style={{ fontSize: 27, fontWeight: "bold", color: 'white', textAlign: 'center' }}>Create your account</Text>
                    <Text style={{ fontSize: 18, color: '#ffb829', textAlign: 'center' }}>Shipping address</Text>
                    <View style={{ marginTop: '10%' }} />

                    <TouchableHighlight
                        style={styles.button}
                        onPress={() => /*this.Signup(navigate) */ navigate('SignupProfilePic')}
                        disabled={true}
                    >

                        <Text style={styles.buttonText}>Add a new address</Text>
                    </TouchableHighlight>

                    <View style={{ marginTop: '6%' }} />

                    <Text
                        style={{ fontWeight: "bold", color: "white", textAlign: 'center' }}
                        onPress={() => navigate('Home')} >
                        {this.state.pictureChosen ? '' : 'Skip'}
                    </Text>
                </View>

                <Text style={{ fontSize: 18, color: 'white', textAlign: 'center' }}>
                    Your addresses
                </Text>

                <View style={styles.bottomContainer} renderItem={this.renderItem}>
                    {isLoading ? <ActivityIndicator /> : (
                        <FlatList
                            data={data}
                            renderItem={({ item }) => (
                                <View style={styles.addressContainer}>
                                    <RadioButton
                                        value={item.ID}
                                        status={item.primaryAddress ? 'checked' : 'unchecked'}
                                        onPress={() => { this.checkRadioButton(item.primaryAddress, item.ID) }}
                                    />
                                    <View style={{ paddingLeft: 10 }}>
                                        <Text style={{ fontWeight: "bold" }}>{item.addressee}</Text>
                                        <Text>{item.street} {item.number}</Text>
                                        <Text>{item.city}, {item.province} {item.zipCode}</Text>
                                        <Text>{item.country}</Text>
                                        <Text>Phone number: {item.phone}</Text>
                                    </View>
                                </View>
                            )}
                        />
                    )}
                </View>
            </View>
        )
    }

}

const faidingBg = require("./images/fadingBg.jpg");

const styles = StyleSheet.create({
    upperContainer: {
        marginTop: "20%",
        marginLeft: '10%',
        marginRight: '10%',
        height: "28%"
        //marginTop: 100,
    },
    bottomContainer: {
        marginLeft: '10%',
        marginRight: '10%',
        height: "54%"
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
        height: '25%',
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
    },
    addressContainer: {
        backgroundColor: 'white',
        borderColor: 'gray',
        borderWidth: 1.5,
        borderRadius: 10,
        paddingLeft: 10,
        paddingTop: 10,
        paddingBottom: 10,
        marginTop: 2.5,
        flexDirection: 'row'

    }
});

