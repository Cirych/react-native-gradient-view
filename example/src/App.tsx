import * as React from 'react';

import { StyleSheet, View, Text, processColor } from 'react-native';
import { GradientViewView } from 'react-native-gradient-view';

const colorList = [
  { offset: '0%', color: 'blue' },
  { offset: '29%', color: '#00AA00ee' }, // opacity
  { offset: '68%', color: 'red' },
  { offset: '100%', color: '#FFF800' },
];

const colorListModule = colorList.map(({ color, ...rest }) => ({
  color: processColor(color),
  ...rest,
}));

export default function App() {
  return (
    <View style={styles.container}>
      <View style={styles.card}>
        <Text style={styles.text}>
          {Array(26)
            .fill(97)
            .map((x, y) => String.fromCharCode(x + y))}
        </Text>
        <GradientViewView
          // colors={['red', '#00a852dd', '#ffa852', 'brown', 'blue']}
          // locations={[0.0, 0.2, 0.5, 0.6, 0.8, 1.0]}
          // start={{x: 0.0, y: 0.25}} end={{x: 0.5, y: 1.0}} <-- shift to left!
          // start={{ x: 0.5, y: 0.0 }}
          // end={{ x: 0.5, y: 1.0 }}
          colorList={colorListModule}
          angle={15}
          style={styles.box}
        />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'grey',
  },
  card: {
    width: 300,
    height: 400,
    backgroundColor: 'lightgrey',
  },
  text: {
    fontSize: 70,
    width: 250,
    height: 350,
    margin: 20,
    color: 'white',
    backgroundColor: 'black',
  },
  box: {
    position: 'absolute',
    top: 0,
    // left: 50,
    width: 200,
    height: 400,
    // margin: 10,
    // backgroundColor: 'blue',
    transform: [{ rotate: '-15deg' }],
  },
});
