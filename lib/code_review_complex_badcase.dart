class Calculator {
  String _input = '';
  String _output = '';

  void addDigit(String digit) {
    if (digit == null) {
      throw new NullArgumentException('digit');
    }
    _input += digit;
    _updateOutput();
  }

  void addOperator(String operator) {
    if (operator == null) {
      throw new NullArgumentException('operator');
    }
    _input += ' $operator ';
    _updateOutput();
  }

  void clear() {
    _input = '';
    _output = '';
  }

  void calculate() {
    List<String> tokens = _input.split(' ');
    Stack<String> stack = Stack<String>();
    for (var token in tokens) {
      if (_isOperator(token)) {
        String op2 = stack.pop();
        String op1 = stack.pop();
        String result = _applyOperator(op1, token, op2);
        stack.push(result);
      } else {
        stack.push(token);
      }
    }
    _output = stack.pop();
  }

  void _updateOutput() {
    _output = _input.replaceAll(' ', '');
  }

  bool _isOperator(String token) {
    return token == '+' || token == '-' || token == '*' || token == '/';
  }

  String _applyOperator(String op1, String operator, String op2) {
    double num1 = double.parse(op1);
    double num2 = double.parse(op2);
    double result;
    switch (operator) {
      case '+':
        result = num1 + num2;
        break;
      case '-':
        result = num1 - num2;
        break;
      case '*':
        result = num1 * num2;
        break;
      case '/':
        result = num1 / num2;
        break;
    }
    return result.toString();
  }
}

class NullArgumentException implements Exception {
  final String paramName;

  NullArgumentException(this.paramName);

  @override
  String toString() {
    return '$paramName cannot be null';
  }
}

void main() {
  Calculator calculator = Calculator();

  // Adding digits and operators
  calculator.addDigit('1');
  calculator.addOperator('+');
  calculator.addDigit('2');
  calculator.addOperator('*');
  calculator.addDigit('3');
  calculator.addOperator('-');
  calculator.addDigit('4');
  calculator.addOperator('/');
  calculator.addDigit('5');

  // Calculating the result
  calculator.calculate();

  // Clearing the input
  calculator.clear();
}