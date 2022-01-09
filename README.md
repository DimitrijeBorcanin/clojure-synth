# clojure-synth

An app designed to output monophonic sound waves (sine, square, sawtooth).

This app generates basic sound waves to generate monophonic tones. To play a tone or a melody, you have to supply notes; they are represented as vectors with three attributes - pitch, volume and duration. These attributes are based on something you might find in a sheet music programs. 

Pitch is is represented as as string containing a note and it's octave (eg A4). App is based around A4 pitch set to 440Hz. All the other notes are then calculated on the spot, meaning you can change the value of A4 and all other notes will change during execution, based on equal temperament system. Pitches range from C0 to B8, any other pitches are unsupported and will throw an error.

Volume can be in the range from 0 to a 100. The lowest value - 0 - can be used as a pause.

Duration consist of 7 posible numbers to choose from: 1, 2, 4, 8, 16, 32, 64. These numbers represent the denominator of a fraction that represents note's duration. Quarter notes are the base of all other duration. They are represented as 4. When a certain bpm is supplied, app calculates the duration of a quarter note in milliseconds. After that all other durations are calculated by their ratio to the quarter note.

## Quick Start

If you want to test this app, you can play the default tune easily! Type in lein run and you will be prompted to press enter. After that, a short audio sample will ring out. Alternatively, follow the instructions to play your melody.

## Usage

Playing through command line:

1. Type in lein run in your CLI.

2. Test out predefined melody by typing in test or press enter to start making your melody.

3. Enter beats per minute; value needs to be in range from 30 to 300.

4. Choose oscillators by entering the corresponding number (1 - sine, 2 - square, 3 - sawtooth).

5. Start inputing notes: pitch values need to be between C0 and B8, volume needs to be between 0 and 100 (0 can be used as a pause) and duration must be one of the following values: 1, 2, 4, 8, 16, 32, 64 - numbers represent the denominator of a fraction, eg. 4 is 1/4 or quarter note.

6. When you are done inputing your melody type in done. Melody will play. 

7. After that you can continue using the app from the start or exit anytime by typing in exit.

Playing through code:

1. Define a custom format using audio-formats/custom-format function and pass sample rate, number of bits and number of channels as arguments
    OR 
    Use default format which is already defined as "af" variable.

2. Make your own melody by defining a vector containing note vectors. A note vector consists of pitch (C0 - B8), volume (0 - 100) and duration (1, 2, 4, 8, 16, 32, 64 - numbers represent the denominator of a fraction, eg. 4 is 1/4 or quarter note). You can use any pitch combined with 0 volume to get a pause. Eg. ["B4" 100 8]
    OR
    Use predefined melodies. They are stored in variables (rondo-alla-turca or samo-mi-kazi).

3. Choose oscillator function to be used for playback. Currently available oscillators are sine, square and sawtooth.

4. Play the melody! You can play your or predefined melody by calling the play-melody function and passing in audio format, oscillator, melody and beats per minute. Eg (play-melody af sine rondo-alla-turca 260)

# Future updates

This app is easy to expand with many features. Some of those features are:
- More oscillators
- Playing the melody indefinitely or reacting to a key press on keyboard (therefore, computer keyboard can be used as an actual muscal keyboard)
- Dotted durations
- Erasing notes in CLI melody input
- Case insensitive pitch