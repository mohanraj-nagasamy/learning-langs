from collections import namedtuple
import collections
import datetime
from time import sleep

drinks = ["coffee", "tea", "milk", "water"]

for index, drink in enumerate(drinks):
    print("Item {} is {}".format(index, drink))

print(set(['aa', 'bb', 'cc', 'aa']))

# compare lists to find differences/similarities
# {} without "key":"value" pairs makes a set
menu = {"pancakes", "ham", "eggs", "bacon"}
new_menu = {"coffee", "ham", "eggs", "bacon", "bagels"}

new_items = new_menu.difference(menu)
print("Try our new", ", ".join(new_items))
# Try our new bagels, coffee

discontinued_items = menu.difference(new_menu)
print("Sorry, we no longer have", ", ".join(discontinued_items))
# Sorry, we no longer have pancakes

LightObject = namedtuple('LightObject', ['shortname', 'otherprop'])
m = LightObject(shortname='something', otherprop='something else')
# m.shortname = 'athing'
print(m.shortname)


class Event(object):
    def __init__(self, t=None):

        print("Event(self) = {} ".format(t))
        if t is None:
            self.time = datetime.datetime.now()
        else:
            self.time = t


events = collections.defaultdict(Event)

for i in range(5):
    # sleep(1)
    print(events[i].time)


def my_generator(v):
    yield 'first ' + v
    yield 'second ' + v
    yield 'third ' + v


print(my_generator('thing'))

for value in my_generator('thing'):
    print value


# Loop style production
loop = set()
names = ['aa', 'bb', 'cc', 'aa']
for name in names:
    if name != 'Jones':
        loop.add(name)

# Set comprehension production
comprehension = {name for name in names if name != 'Jones'}

result = 'name_set.py produced %s sets with a comprehension and a loop.'
print result % ['different', 'identical'][loop == comprehension]

