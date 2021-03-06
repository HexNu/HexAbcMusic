var midiChannels = [
    {'chanel': '1', 'name': 'Acoustic Grand Piano'},
    {'chanel': '2', 'name': 'Bright Acoustic Piano'},
    {'chanel': '3', 'name': 'Electric Grand Piano'},
    {'chanel': '4', 'name': 'Honky - tonk Piano'},
    {'chanel': '5', 'name': 'Electric Piano 1'},
    {'chanel': '6', 'name': 'Electric Piano 2'},
    {'chanel': '7', 'name': 'Harpsichord'},
    {'chanel': '8', 'name': 'Clavi'},
    {'chanel': '9', 'name': 'Celesta'},
    {'chanel': '10', 'name': 'Glockenspiel'},
    {'chanel': '11', 'name': 'Music Box'},
    {'chanel': '12', 'name': 'Vibraphone'},
    {'chanel': '13', 'name': 'Marimba'},
    {'chanel': '14', 'name': 'Xylophone'},
    {'chanel': '15', 'name': 'Tubular Bells'},
    {'chanel': '16', 'name': 'Dulcimer'},
    {'chanel': '17', 'name': 'Drawbar Organ'},
    {'chanel': '18', 'name': 'Percussive Organ'},
    {'chanel': '19', 'name': 'Rock Organ'},
    {'chanel': '20', 'name': 'Church Organ'},
    {'chanel': '21', 'name': 'Reed Organ'},
    {'chanel': '22', 'name': 'Accordion'},
    {'chanel': '23', 'name': 'Harmonica'},
    {'chanel': '24', 'name': 'Tango Accordion'},
    {'chanel': '25', 'name': 'Acoustic Guitar (nylon)'},
    {'chanel': '26', 'name': 'Acoustic Guitar (steel)'},
    {'chanel': '27', 'name': 'Electric Guitar (jazz)'},
    {'chanel': '28', 'name': 'Electric Guitar (clean)'},
    {'chanel': '29', 'name': 'Electric Guitar (muted)'},
    {'chanel': '30', 'name': 'Overdriven Guitar'},
    {'chanel': '31', 'name': 'Distortion Guitar'},
    {'chanel': '32', 'name': 'Guitar harmonics'},
    {'chanel': '33', 'name': 'Acoustic Bass'},
    {'chanel': '34', 'name': 'Electric Bass (finger)'},
    {'chanel': '35', 'name': 'Electric Bass (pick)'},
    {'chanel': '36', 'name': 'Fretless Bass'},
    {'chanel': '37', 'name': 'Slap Bass 1'},
    {'chanel': '38', 'name': 'Slap Bass 2'},
    {'chanel': '39', 'name': 'Synth Bass 1'},
    {'chanel': '40', 'name': 'Synth Bass 2'},
    {'chanel': '41', 'name': 'Violin'},
    {'chanel': '42', 'name': 'Viola'},
    {'chanel': '43', 'name': 'Cello'},
    {'chanel': '44', 'name': 'Contrabass'},
    {'chanel': '45', 'name': 'Tremolo Strings'},
    {'chanel': '46', 'name': 'Pizzicato Strings'},
    {'chanel': '47', 'name': 'Orchestral Harp'},
    {'chanel': '48', 'name': 'Timpani'},
    {'chanel': '49', 'name': 'String Ensemble 1'},
    {'chanel': '50', 'name': 'String Ensemble 2'},
    {'chanel': '51', 'name': 'SynthStrings 1'},
    {'chanel': '52', 'name': 'SynthStrings 2'},
    {'chanel': '53', 'name': 'Choir Aahs'},
    {'chanel': '54', 'name': 'Voice Oohs'},
    {'chanel': '55', 'name': 'Synth Voice'},
    {'chanel': '56', 'name': 'Orchestra Hit'},
    {'chanel': '57', 'name': 'Trumpet'},
    {'chanel': '58', 'name': 'Trombone'},
    {'chanel': '59', 'name': 'Tuba'},
    {'chanel': '60', 'name': 'Muted Trumpet'},
    {'chanel': '61', 'name': 'French Horn'},
    {'chanel': '62', 'name': 'Brass Section'},
    {'chanel': '63', 'name': 'SynthBrass 1'},
    {'chanel': '64', 'name': 'SynthBrass 2'},
    {'chanel': '65', 'name': 'Soprano Sax'},
    {'chanel': '66', 'name': 'Alto Sax'},
    {'chanel': '67', 'name': 'Tenor Sax'},
    {'chanel': '68', 'name': 'Baritone Sax'},
    {'chanel': '69', 'name': 'Oboe'},
    {'chanel': '70', 'name': 'English Horn'},
    {'chanel': '71', 'name': 'Bassoon'},
    {'chanel': '72', 'name': 'Clarinet'},
    {'chanel': '73', 'name': 'Piccolo'},
    {'chanel': '74', 'name': 'Flute'},
    {'chanel': '75', 'name': 'Recorder'},
    {'chanel': '76', 'name': 'Pan Flute'},
    {'chanel': '77', 'name': 'Blown Bottle'},
    {'chanel': '78', 'name': 'Shakuhachi'},
    {'chanel': '79', 'name': 'Whistle'},
    {'chanel': '80', 'name': 'Ocarina'},
    {'chanel': '81', 'name': 'Lead 1 (square)'},
    {'chanel': '82', 'name': 'Lead 2 (sawtooth)'},
    {'chanel': '83', 'name': 'Lead 3 (calliope)'},
    {'chanel': '84', 'name': 'Lead 4 (chiff)'},
    {'chanel': '85', 'name': 'Lead 5 (charang)'},
    {'chanel': '86', 'name': 'Lead 6 (voice)'},
    {'chanel': '87', 'name': 'Lead 7 (fifths)'},
    {'chanel': '88', 'name': 'Lead 8 (bass + lead)'},
    {'chanel': '89', 'name': 'Pad 1 (new age)'},
    {'chanel': '90', 'name': 'Pad 2 (warm)'},
    {'chanel': '91', 'name': 'Pad 3 (polysynth)'},
    {'chanel': '92', 'name': 'Pad 4 (choir)'},
    {'chanel': '93', 'name': 'Pad 5 (bowed)'},
    {'chanel': '94', 'name': 'Pad 6 (metallic)'},
    {'chanel': '95', 'name': 'Pad 7 (halo)'},
    {'chanel': '96', 'name': 'Pad 8 (sweep)'},
    {'chanel': '97', 'name': 'FX 1 (rain)'},
    {'chanel': '98', 'name': 'FX 2 (soundtrack)'},
    {'chanel': '99', 'name': 'FX 3 (crystal)'},
    {'chanel': '100', 'name': 'FX 4 (atmosphere)'},
    {'chanel': '101', 'name': 'FX 5 (brightness)'},
    {'chanel': '102', 'name': 'FX 6 (goblins)'},
    {'chanel': '103', 'name': 'FX 7 (echoes)'},
    {'chanel': '104', 'name': 'FX 8 (sci - fi)'},
    {'chanel': '105', 'name': 'Sitar'},
    {'chanel': '106', 'name': 'Banjo'},
    {'chanel': '107', 'name': 'Shamisen'},
    {'chanel': '108', 'name': 'Koto'},
    {'chanel': '109', 'name': 'Kalimba'},
    {'chanel': '110', 'name': 'Bag pipe'},
    {'chanel': '111', 'name': 'Fiddle'},
    {'chanel': '112', 'name': 'Shanai'},
    {'chanel': '113', 'name': 'Tinkle Bell'},
    {'chanel': '114', 'name': 'Agogo'},
    {'chanel': '115', 'name': 'Steel Drums'},
    {'chanel': '116', 'name': 'Woodblock'},
    {'chanel': '117', 'name': 'Taiko Drum'},
    {'chanel': '118', 'name': 'Melodic Tom'},
    {'chanel': '119', 'name': 'Synth Drum'},
    {'chanel': '120', 'name': 'Reverse Cymbal'},
    {'chanel': '121', 'name': 'Guitar Fret Noise'},
    {'chanel': '122', 'name': 'Breath Noise'},
    {'chanel': '123', 'name': 'Seashore'},
    {'chanel': '124', 'name': 'Bird Tweet'},
    {'chanel': '125', 'name': 'Telephone Ring'},
    {'chanel': '126', 'name': 'Helicopter'},
    {'chanel': '127', 'name': 'Applause'},
    {'chanel': '128', 'name': 'Gunshot'}
]
