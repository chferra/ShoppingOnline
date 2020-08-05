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
  Image,
  Button,
  DrawerLayoutAndroidComponent
} from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import * as ImagePicker from 'expo-image-picker';
import Constants from 'expo-constants';
import * as Permissions from 'expo-permissions';
import * as FileSystem from 'expo-file-system';

export default class SignupProfilePicScreen extends Component {

  state = {
    isSigningUp: false,
    nav: this.props.navigator,
    pictureChosen: false,
    image: null,
  }

  SignupProfilePic = async (navigate) => {

    //this.setState({isSigningUp: true});
    const base64Image = await FileSystem.readAsStringAsync(this.state.image, { encoding: 'base64' });

    fetch('http://' + global.serverIP + ':8080/ShoppingOnlineWS/resources/picture', {
      method: 'POST',
      headers: {
        'Accept': 'text/plain',
        'Content-Type': 'application/json'
      },
      body:
        JSON.stringify({
          imagePath: this.state.image,
          imageData: base64Image
        })
    }).then((response) => response.text()).then((text) => {
      
        const IdProfilePic = text;
        alert(IdProfilePic);

        fetch('http://' + global.serverIP + ':8080/ShoppingOnlineWS/resources/user/profilePicture', {
          method: 'PUT',
          headers: {
            'Content-Type': 'text/plain'
          },
          body:
            IdProfilePic            
        }).then(function (secondResponse) {
          if (secondResponse.status === 204)
            navigate('SignupAddresses');
          else
            Alert.alert("Error", "Something went wrong with uploading your picture. Try again");

        }).catch((error) => {
          console.log("Api call error");
          alert(error.message);
        });

    }).catch((error) => {
      console.log("Api call error");
      alert(error.message);
    });

  }

  componentDidMount() {
    this.getPermissionAsync();
  }

  getPermissionAsync = async () => {
    if (Constants.platform.ios) {
      const { status } = await Permissions.askAsync(Permissions.CAMERA_ROLL);
      if (status !== 'granted') {
        alert('Sorry, we need camera roll permissions to make this work!');
      }
    }
  };

  _pickImage = async () => {
    try {
      let result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Images,
        allowsEditing: true,
        aspect: [4, 3],
        quality: 1,
      });
      if (!result.cancelled) {
        this.setState({ image: result.uri });
        this.setState({ pictureChosen: true });
      }
      alert(image);

      console.log(result);
    } catch (E) {
      console.log(E);
    }
  };

  render() {
    const { navigate } = this.props.navigation;
    let { image } = this.state;

    var imageToDisplay = image
      ? { uri: image }
      : require('./images/blankPicture.png');

    return (
      <View>

        <ImageBackground source={faidingBg} style={styles.image} />


        <ScrollView style={styles.container} >

          <Text style={{ fontSize: 27, fontWeight: "bold", color: 'white', textAlign: 'center' }}>Create your account</Text>
          <Text style={{ fontSize: 18, color: '#ffb829', textAlign: 'center' }}>Choose a profile picture</Text>

          <View style={{ marginTop: '10%' }} />

          <View>
            <Image source={imageToDisplay} style={styles.picture} />
          </View>

          <View style={{ marginTop: '5%' }} />
          <Text
            style={{ fontWeight: "bold", color: "white", textAlign: 'center' }}
            onPress={this._pickImage}  >
            {this.state.pictureChosen ? 'Change photo' : ''}
          </Text>

          <View style={{ marginTop: '10%' }} />
          {
            !this.state.pictureChosen ?
              <TouchableHighlight
                style={styles.button}
                onPress={this._pickImage} >
                <Text style={styles.buttonText}>Add a photo</Text>
              </TouchableHighlight>
              :
              <TouchableHighlight
                style={styles.button}
                onPress={() => this.SignupProfilePic(navigate)} >
                <Text style={styles.buttonText}>Next</Text>
              </TouchableHighlight>
          }

          <View style={{ marginTop: '10%' }} />
        </ScrollView>

        <View style={{ marginTop: '53.5%' }} />
        <Text
          style={{ fontWeight: "bold", color: "white", textAlign: 'center' }}
          onPress={() => navigate('SignupAddresses')} >
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

