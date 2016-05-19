from setuptools.command.bdist_egg import analyze_egg

if "":
    print("expr is True")
else:
    print("else case")

c = 5

while c != 0:
    print("version1 : " + str(c))
    c -= 1

c = 5
while c:
    print("version2 : " + str(c))
    c -= 1

# str

'he;;o'[1]

# list

a = [1, 4, 5]
a[1]
a = ["apple", "oranve", "pear"]
a[2] = "pear222"
a[2]
# a[3] = "pear333"
# a[3]


# from urllib.request import urlopen
#
# with urlopen("http://sixty-north.com/c/t.txt") as story:
#     story_words = []
#     for line in story:
#         print(line.decode("utf-8"))


def square(x):
    return x * x


square(10)

# Collections

# - tuple

t = ("Color", 12, 3434.343)

for item in t:
    print(item)

t = t + ("one", "two")

print(t)

intt = (1)
print(type(intt))

intt = () + (1,)
print(type(intt))

# Exceptions


def convert(s):
    """ convert to an integer """
    try:
        x = int(s)
    except ValueError:
        x = -1
    except TypeError:
        x = -1
    return x


def convert(s):
    """ convert to an integer """
    try:
        x = int(s)
    except (ValueError, TypeError) as e:
        print(type(e))
        x = -1
        # raise
    return x


print(convert("s"))
print(convert([1, 2, 3]))

# Generators

def gen123():
    yield 1
    yield 2
    yield 3


g = gen123()

print(next(g))
print(next(g))
print(next(g))
# print(next(g))


def gen246():
    print("About to yield 2")
    yield 2
    print("About to yield 4")
    yield 4
    print("About to yield 6")
    yield 6
    print("About to return")


g = gen246()
print(next(g))
print(next(g))
print(next(g))
# print(next(g))


def take(count, iteratable):
    itrator = iter(iteratable)

    counter = 0
    for item in itrator:
        if count == counter:
            return
        counter += 1
        yield item


print("take")
for item in take(3, range(10, 20)):
    print(item)


def lucas():
    yield 2
    a = 2
    b = 1
    while True:
        yield b
        a, b = b, a + b


print("lucas")
for item in take(10, lucas()):
    print(item)

# Class

# Minimal class
class Flight:
    pass


# Class with one instance methods
class Flight:
    def number(self):
        """ Instance methods must pass parameter called self on their first position """
        print("self {}".format(self))
        return "DL567"


f = Flight()

f.number()  # You can call Flight.number(f)

# Class with  Initializer (not constructor) __init__
class Flight:
    def __init__(self, number, aircraft):
        """ Initializer """
        if not number[:2].isalpha():
            raise ValueError("No airline code in {}".format(number))

        if not number[:2].isupper():
            raise ValueError("Invalid airline code {}".format(number))

        if not (number[2:].isdigit() and int(number[2:]) <= 9999):
            raise ValueError("Invalid routing number {}".format(number))

        self._number = number
        self._aircraft = aircraft

        rows, seats = self._aircraft.seating_plan()
        self._seating = [None] + [{letter: None for letter in seats} for _ in rows]


    def number(self):
        """ Instance methods must pass parameter called self on their first position """
        print("self {}".format(self))
        return self._number

    def airline(self):
        return self._number[:2]

    def aircraft_model(self):
        return self._aircraft.model()

    def _parse_seat(self, seat):
        rows, seat_letters = self._aircraft.seating_plan()
        letter = seat[-1]
        if letter not in seat_letters:
            raise ValueError("Invalid seatletter {}".format(letter))
        row_text = seat[:1]
        try:
            row = int(row_text)
        except ValueError:
            raise ValueError("Invalid row seat {}".format(row_text))
        if row not in rows:
            raise ValueError("Invalid row number {}".format(row))

        return letter, row


    def allocate_seat(self, seat, passenger):
        letter, row = self._parse_seat(seat)

        if self._seating[row][letter] is not None:
            raise ValueError("Seat {} is already occupied.".format(seat))

        self._seating[row][letter] = passenger


# f = Flight("DL125")
#
# f.number()  # You can call Flight.number(f)


class Aircraft:
    def __init__(self, registration, model, row_nums, num_seats_per_row):
        self._num_seats_per_row = num_seats_per_row
        self._row_nums = row_nums
        self._model = model
        self.__registration = registration


    def registration(self):
        return self.__registration


    def model(self):
        return self._model


    def seating_plan(self):
        return range(1, self._row_nums + 1), "ABCDEFGHJ"[:self._num_seats_per_row]


a = Aircraft("G-EUPT", "Airbus A319", row_nums=22, num_seats_per_row=6)
a.registration()
a.model()

f = Flight("DL125", a)

rows, seats = f._aircraft.seating_plan()
f._seating = [None] + [{letter: None for letter in seats} for _ in rows]
# print(f._seating)

from pprint import pprint as pp
# pp(f._seating)

f.allocate_seat("12A", "mohanraj")
f.allocate_seat("32A", "priya")
# f.allocate_seat("32A", "priya")

pp(f._seating)


# TestCase
# Fixture - expected env -

import unittest
import os


def analyze_text(filename):
    pass


class TextAnalysisTests(unittest.TestCase):
    """ Tests for for analyze_text() """

    def setUp(self):
        self.filename = "text_alaysis_test_file.txt"

        with open(self.filename, "w") as f:
            f.write("some test asfd asdfjlk asjfdlkaj slkfkjas \n"
                    "asfdsa jfasklj ksajdflkjasd")


    def tearDown(self):
        try:
            os.remove(self.filename)
        except:
            pass


    def test_function_runs_analyze_text(self):
        analyze_text(self.filename)


unittest.main

# Installing
# Distutils, easy_install, pip

# pypi.python.org/pypi



class Horse(object):
    """Horse represends a Horse"""

    def __init__(self, color):
        print ""

