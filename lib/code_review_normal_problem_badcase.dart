class ShoppingCart {
  List<String> items = [];

  void addItem(String item) {
    if (item == null) {
      throw new NullArgumentException('item');
    }
    items.add(item);
  }

  void removeItem(int index) {
    if (index < 0 || index >= items.length) {
      throw new RangeError('index out of bounds: $index');
    }
    items.removeAt(index);
  }

  void addItems(List<String> newItems) {
    if (newItems == null) {
      throw new NullArgumentException('newItems');
    }
    for (var item in newItems) {
      if (item == null) {
        continue;
      }
      items.add(item);
    }
  }

  void checkout() {
    while (items.length > 0) {
      // do something
    }
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
  ShoppingCart cart = ShoppingCart();

  // Adding items to the cart
  try {
    cart.addItem(null); // Throws NullArgumentException
  } catch (e) {
    print(e.toString());
  }

  cart.addItem('Item 1');
  cart.addItem('Item 2');
  cart.addItem('Item 3');

  // Removing an item from the cart
  try {
    cart.removeItem(10); // Throws RangeError
  } catch (e) {
    print(e.toString());
  }

  cart.removeItem(1);

  // Adding multiple items to the cart
  try {
    cart.addItems(null); // Throws NullArgumentException
  } catch (e) {
    print(e.toString());
  }

  cart.addItems(['Item 4', null, 'Item 5']);

  // Checking out the cart
  cart.checkout(); // This loop will never end
}