import 'package:flutter/material.dart';

class RadioButtonDemo extends StatefulWidget {
  @override
  _RadioButtonDemoState createState() => _RadioButtonDemoState();
}

class _RadioButtonDemoState extends State<RadioButtonDemo> {
  String _selectedOption = '';

  void _handleRadioValueChanged(String value) {
    setState(() {
      _selectedOption = value;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Radio Button Demo')),
      body: Container(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('Select your favorite color:'),
            RadioListTile(
              title: Text('Red'),
              value: 'red',
              groupValue: _selectedOption,
              onChanged: _handleRadioValueChanged,
            ),
            RadioListTile(
              title: Text('Green'),
              value: 'green',
              groupValue: _selectedOption,
              onChanged: _handleRadioValueChanged,
            ),
            RadioListTile(
              title: Text('Blue'),
              value: 'blue',
              groupValue: _selectedOption,
              onChanged: _handleRadioValueChanged,
            ),
            SizedBox(height: 16.0),
            Text('Selected option: $_selectedOption'),
          ],
        ),
      ),
    );
  }
}
