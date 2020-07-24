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
    Image
} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import ImagePicker from 'react-native-image-picker'


export default class SignupProfilePicScreen extends Component {

    state = {
        isSigningUp: false,
        nav: this.props.navigator,
        pictureChosen: false
    }



    SignupProfilePic = async (navigate) => {

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

    chooseImage = () => {
        let options = {
          title: 'Select Image',
          customButtons: [
            { name: 'customOptionKey', title: 'Choose Photo from Custom Option' },
          ],
          storageOptions: {
            skipBackup: true,
            path: 'images',
          },
        };
        ImagePicker.showImagePicker(options, (response) => {
          console.log('Response = ', response);
    
          if (response.didCancel) {
            console.log('User cancelled image picker');
          } else if (response.error) {
            console.log('ImagePicker Error: ', response.error);
          } else if (response.customButton) {
            console.log('User tapped custom button: ', response.customButton);
            alert(response.customButton);
          } else {
            const source = { uri: response.uri };
    
            // You can also display the image using data:
            // const source = { uri: 'data:image/jpeg;base64,' + response.data };
            // alert(JSON.stringify(response));s
            console.log('response', JSON.stringify(response));
            this.setState({
              filePath: response,
              fileData: response.data,
              fileUri: response.uri
            });
          }
        });
      }
    
      launchCamera = () => {
        let options = {
          storageOptions: {
            skipBackup: true,
            path: 'images',
          },
        };
        ImagePicker.launchCamera(options, (response) => {
          console.log('Response = ', response);
    
          if (response.didCancel) {
            console.log('User cancelled image picker');
          } else if (response.error) {
            console.log('ImagePicker Error: ', response.error);
          } else if (response.customButton) {
            console.log('User tapped custom button: ', response.customButton);
            alert(response.customButton);
          } else {
            const source = { uri: response.uri };
            console.log('response', JSON.stringify(response));
            this.setState({
              filePath: response,
              fileData: response.data,
              fileUri: response.uri
            });
          }
        });
    
      }
    
      launchImageLibrary = () => {
        let options = {
          storageOptions: {
            skipBackup: true,
            path: 'images',
          },
        };
        ImagePicker.launchImageLibrary(options, (response) => {
          console.log('Response = ', response);
    
          if (response.didCancel) {
            console.log('User cancelled image picker');
          } else if (response.error) {
            console.log('ImagePicker Error: ', response.error);
          } else if (response.customButton) {
            console.log('User tapped custom button: ', response.customButton);
            alert(response.customButton);
          } else {
            const source = { uri: response.uri };
            console.log('response', JSON.stringify(response));
            this.setState({
              filePath: response,
              fileData: response.data,
              fileUri: response.uri
            });
          }
        });
    
      }
    
      renderFileData() {
        if (this.state.fileData) {
          return <Image source={{ uri: 'data:image/jpeg;base64,' + this.state.fileData }}
            style={styles.images}
          />
        } else {
          return <Image 
            source={require('./images/blankPicture.png')}
            style={styles.images}
          />
        }
      }
    
      renderFileUri() {
        if (this.state.fileUri) {
          return <Image
            source={{ uri: this.state.fileUri }}
            style={styles.images}
          />
        } else {
          return <Image
            source={require('./images/blankPicture.png')}
            style={styles.images}
          />
        }
      }

    render() {
        const { navigate } = this.props.navigation;
        return (


            


            <View>

                <ImageBackground source={faidingBg} style={styles.image} />

                <ScrollView style={styles.container} >

                    <Text style={{ fontSize: 27, fontWeight: "bold", color: 'white', textAlign: 'center' }}>Create your account</Text>
                    <Text style={{ fontSize: 18, color: '#ffb829', textAlign: 'center' }}>Add a profile picture</Text>

                    <View style={{ marginTop: '10%' }} />

                    
                        <Image
                            style={styles.picture}
                            source={require("./images/blankPicture.png")}
                        />


                    

                    <View style={{ marginTop: '5%' }} />
                    <Text style={{ fontWeight: "bold", color: "white", textAlign: 'center' }}   >
                        {this.state.pictureChosen ? 'Change photo' : ''}
                    </Text>

                    <View style={{ marginTop: '10%' }} />
                    <TouchableHighlight
                        style={styles.button}
                        //onPress={this.Login.bind(this)} 
                        onPress={() => this.SignupProfilePic(navigate)}
                        disabled={this.state.isSigningUp || !this.state.name || !this.state.surname || !this.state.date || !this.state.email ||
                            !this.state.password || !this.state.confirmPassword} >
                        <Text style={styles.buttonText}>{this.state.pictureChosen ? 'Next' : 'Add a photo'}</Text>
                    </TouchableHighlight>

                    <View style={{ marginTop: '10%' }} />
                </ScrollView>

                <View style={{ marginTop: '53.5%' }} />
                <Text style={{ fontWeight: "bold", color: "white", textAlign: 'center' }}   >
                    {this.state.pictureChosen ? '' : 'Skip'}
                </Text>

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
        height: '14%',
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
    picture: {
        marginLeft: "27.5%",
        width: 150,
        height: 150,
        borderRadius: 75

    }
});

