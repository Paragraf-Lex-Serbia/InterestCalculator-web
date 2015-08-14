package rs.paragraf.se.calc.interest.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalendarPicker extends JPanel {

	byte icon[] = {

	(byte) 0x0089, (byte) 0x0050, (byte) 0x004E, (byte) 0x0047, (byte) 0x000D,
			(byte) 0x000A, (byte) 0x001A, (byte) 0x000A, (byte) 0x0000,
			(byte) 0x0000, (byte) 0x0000, (byte) 0x000D, (byte) 0x0049,
			(byte) 0x0048, (byte) 0x0044, (byte) 0x0052, (byte) 0x0000,
			(byte) 0x0000, (byte) 0x0000, (byte) 0x0010, (byte) 0x0000,
			(byte) 0x0000, (byte) 0x0000, (byte) 0x0010, (byte) 0x0008,
			(byte) 0x0006, (byte) 0x0000, (byte) 0x0000, (byte) 0x0000,
			(byte) 0x001F, (byte) 0x00F3, (byte) 0x00FF, (byte) 0x0061,
			(byte) 0x0000, (byte) 0x0000, (byte) 0x0000, (byte) 0x0009,
			(byte) 0x0070, (byte) 0x0048, (byte) 0x0059, (byte) 0x0073,
			(byte) 0x0000, (byte) 0x0000, (byte) 0x000B, (byte) 0x0013,
			(byte) 0x0000, (byte) 0x0000, (byte) 0x000B, (byte) 0x0013,
			(byte) 0x0001, (byte) 0x0000, (byte) 0x009A, (byte) 0x009C,
			(byte) 0x0018, (byte) 0x0000, (byte) 0x0000, (byte) 0x0000,
			(byte) 0x0004, (byte) 0x0067, (byte) 0x0041, (byte) 0x004D,
			(byte) 0x0041, (byte) 0x0000, (byte) 0x0000, (byte) 0x00B1,
			(byte) 0x008E, (byte) 0x007C, (byte) 0x00FB, (byte) 0x0051,
			(byte) 0x0093, (byte) 0x0000, (byte) 0x0000, (byte) 0x0000,
			(byte) 0x0020, (byte) 0x0063, (byte) 0x0048, (byte) 0x0052,
			(byte) 0x004D, (byte) 0x0000, (byte) 0x0000, (byte) 0x007A,
			(byte) 0x0025, (byte) 0x0000, (byte) 0x0000, (byte) 0x0080,
			(byte) 0x0083, (byte) 0x0000, (byte) 0x0000, (byte) 0x00F9,
			(byte) 0x00FF, (byte) 0x0000, (byte) 0x0000, (byte) 0x0080,
			(byte) 0x00E9, (byte) 0x0000, (byte) 0x0000, (byte) 0x0075,
			(byte) 0x0030, (byte) 0x0000, (byte) 0x0000, (byte) 0x00EA,
			(byte) 0x0060, (byte) 0x0000, (byte) 0x0000, (byte) 0x003A,
			(byte) 0x0098, (byte) 0x0000, (byte) 0x0000, (byte) 0x0017,
			(byte) 0x006F, (byte) 0x0092, (byte) 0x005F, (byte) 0x00C5,
			(byte) 0x0046, (byte) 0x0000, (byte) 0x0000, (byte) 0x0002,
			(byte) 0x00FE, (byte) 0x0049, (byte) 0x0044, (byte) 0x0041,
			(byte) 0x0054, (byte) 0x0078, (byte) 0x00DA, (byte) 0x0062,
			(byte) 0x00FC, (byte) 0x00FF, (byte) 0x00FF, (byte) 0x003F,
			(byte) 0x0003, (byte) 0x0025, (byte) 0x0000, (byte) 0x0020,
			(byte) 0x0080, (byte) 0x0058, (byte) 0x0040, (byte) 0x0044,
			(byte) 0x00CA, (byte) 0x00B4, (byte) 0x0033, (byte) 0x00AF,
			(byte) 0x00FF, (byte) 0x00FD, (byte) 0x00FB, (byte) 0x00C7,
			(byte) 0x0040, (byte) 0x00C8, (byte) 0x00A8, (byte) 0x00AF,
			(byte) 0x003F, (byte) 0x00FE, (byte) 0x0031, (byte) 0x00FC,
			(byte) 0x00FD, (byte) 0x00FB, (byte) 0x009F, (byte) 0x00E1,
			(byte) 0x00FD, (byte) 0x0097, (byte) 0x003F, (byte) 0x000C,
			(byte) 0x00A2, (byte) 0x00FC, (byte) 0x006C, (byte) 0x000C,
			(byte) 0x002B, (byte) 0x004B, (byte) 0x00CC, (byte) 0x0045,
			(byte) 0x0001, (byte) 0x0002, (byte) 0x0088, (byte) 0x00C5,
			(byte) 0x00A5, (byte) 0x00EE, (byte) 0x00D8, (byte) 0x001F,
			(byte) 0x003D, (byte) 0x0009, (byte) 0x0079, (byte) 0x00E6,
			(byte) 0x009F, (byte) 0x00FF, (byte) 0x00FE, (byte) 0x0030,
			(byte) 0x0030, (byte) 0x0032, (byte) 0x0032, (byte) 0x0030,
			(byte) 0x00C0, (byte) 0x001C, (byte) 0x0084, (byte) 0x00CE,
			(byte) 0x00FE, (byte) 0x00F6, (byte) 0x00F3, (byte) 0x002F,
			(byte) 0x0083, (byte) 0x0004, (byte) 0x0027, (byte) 0x0033,
			(byte) 0x0083, (byte) 0x00A2, (byte) 0x0038, (byte) 0x0007,
			(byte) 0x00C3, (byte) 0x00F1, (byte) 0x00EB, (byte) 0x009F,
			(byte) 0x0019, (byte) 0x00CA, (byte) 0x007C, (byte) 0x00E4,
			(byte) 0x0018, (byte) 0x00BC, (byte) 0x009B, (byte) 0x008E,
			(byte) 0x00FF, (byte) 0x0001, (byte) 0x0008, (byte) 0x0020,
			(byte) 0x0096, (byte) 0x00DB, (byte) 0x00CF, (byte) 0x00BF,
			(byte) 0x007E, (byte) 0x00F9, (byte) 0x00FC, (byte) 0x00E9,
			(byte) 0x0039, (byte) 0x003F, (byte) 0x0003, (byte) 0x0023,
			(byte) 0x00D0, (byte) 0x0074, (byte) 0x00A0, (byte) 0x002B,
			(byte) 0x0098, (byte) 0x0098, (byte) 0x0098, (byte) 0x00C0,
			(byte) 0x009A, (byte) 0x00FE, (byte) 0x00FD, (byte) 0x00FB,
			(byte) 0x000B, (byte) 0x0064, (byte) 0x0033, (byte) 0x0083,
			(byte) 0x00D9, (byte) 0x00FF, (byte) 0x0081, (byte) 0x00E2,
			(byte) 0x00FF, (byte) 0x0018, (byte) 0x0018, (byte) 0x0019,
			(byte) 0x0004, (byte) 0x0079, (byte) 0x0098, (byte) 0x0019,
			(byte) 0x003E, (byte) 0x00FC, (byte) 0x0060, (byte) 0x0063,
			(byte) 0x00F8, (byte) 0x00F0, (byte) 0x00F3, (byte) 0x000F,
			(byte) 0x00C3, (byte) 0x00AC, (byte) 0x00DD, (byte) 0x0077,
			(byte) 0x0019, (byte) 0x00EE, (byte) 0x00BD, (byte) 0x00FC,
			(byte) 0x00F6, (byte) 0x0005, (byte) 0x0020, (byte) 0x0000,
			(byte) 0x0000, (byte) 0x0041, (byte) 0x0000, (byte) 0x00BE,
			(byte) 0x00FF, (byte) 0x0004, (byte) 0x0004, (byte) 0x0004,
			(byte) 0x00FC, (byte) 0x0000, (byte) 0x007F, (byte) 0x005E,
			(byte) 0x001A, (byte) 0x0000, (byte) 0x001F, (byte) 0x0015,
			(byte) 0x000A, (byte) 0x0000, (byte) 0x00E5, (byte) 0x00E6,
			(byte) 0x00E5, (byte) 0x0000, (byte) 0x0016, (byte) 0x0015,
			(byte) 0x0016, (byte) 0x0000, (byte) 0x0003, (byte) 0x0003,
			(byte) 0x0003, (byte) 0x0000, (byte) 0x00E6, (byte) 0x00E7,
			(byte) 0x00E7, (byte) 0x0000, (byte) 0x001A, (byte) 0x0019,
			(byte) 0x0019, (byte) 0x0000, (byte) 0x00FD, (byte) 0x00FD,
			(byte) 0x00FD, (byte) 0x0000, (byte) 0x00EA, (byte) 0x00EB,
			(byte) 0x00EA, (byte) 0x0000, (byte) 0x001B, (byte) 0x001B,
			(byte) 0x001B, (byte) 0x0000, (byte) 0x00F7, (byte) 0x00F6,
			(byte) 0x00F8, (byte) 0x0000, (byte) 0x00EB, (byte) 0x00EC,
			(byte) 0x00ED, (byte) 0x0000, (byte) 0x001D, (byte) 0x001D,
			(byte) 0x001E, (byte) 0x0000, (byte) 0x00DB, (byte) 0x00E6,
			(byte) 0x00F4, (byte) 0x0000, (byte) 0x00BB, (byte) 0x00D5,
			(byte) 0x00FA, (byte) 0x0000, (byte) 0x0002, (byte) 0x0088,
			(byte) 0x0085, (byte) 0x009D, (byte) 0x0095, (byte) 0x0089,
			(byte) 0x0041, (byte) 0x004F, (byte) 0x0041, (byte) 0x0098,
			(byte) 0x0041, (byte) 0x0046, (byte) 0x0088, (byte) 0x009D,
			(byte) 0x0041, (byte) 0x009C, (byte) 0x00ED, (byte) 0x001B,
			(byte) 0x0083, (byte) 0x0088, (byte) 0x00B8, (byte) 0x0028,
			(byte) 0x00D8, (byte) 0x0056, (byte) 0x0049, (byte) 0x008E,
			(byte) 0x001F, (byte) 0x0040, (byte) 0x00B6, (byte) 0x0010,
			(byte) 0x0098, (byte) 0x00FD, (byte) 0x0086, (byte) 0x00FB,
			(byte) 0x0037, (byte) 0x0090, (byte) 0x002D, (byte) 0x0000,
			(byte) 0x0064, (byte) 0x00FD, (byte) 0x0065, (byte) 0x0078,
			(byte) 0x00C3, (byte) 0x00FF, (byte) 0x000F, (byte) 0x00CC,
			(byte) 0x0016, (byte) 0x00E0, (byte) 0x00E2, (byte) 0x0064,
			(byte) 0x00E0, (byte) 0x0060, (byte) 0x0063, (byte) 0x0062,
			(byte) 0x0000, (byte) 0x0008, (byte) 0x00C0, (byte) 0x00F1,
			(byte) 0x0018, (byte) 0x00A4, (byte) 0x0000, (byte) 0x000C,
			(byte) 0x00C2, (byte) 0x0000, (byte) 0x002C, (byte) 0x0083,
			(byte) 0x0089, (byte) 0x00E0, (byte) 0x006E, (byte) 0x00FB,
			(byte) 0x00FF, (byte) 0x001F, (byte) 0x005D, (byte) 0x00AD,
			(byte) 0x0015, (byte) 0x00EA, (byte) 0x0040, (byte) 0x00A7,
			(byte) 0x00BB, (byte) 0x00E5, (byte) 0x0012, (byte) 0x0048,
			(byte) 0x00CE, (byte) 0x00B0, (byte) 0x002A, (byte) 0x00F3,
			(byte) 0x0053, (byte) 0x00B8, (byte) 0x0063, (byte) 0x0042,
			(byte) 0x009B, (byte) 0x0013, (byte) 0x0044, (byte) 0x00FE,
			(byte) 0x008D, (byte) 0x00CD, (byte) 0x0051, (byte) 0x0095,
			(byte) 0x00B1, (byte) 0x000A, (byte) 0x00AA, (byte) 0x001F,
			(byte) 0x005C, (byte) 0x001D, (byte) 0x00FA, (byte) 0x003B,
			(byte) 0x0031, (byte) 0x0087, (byte) 0x0064, (byte) 0x0046,
			(byte) 0x0096, (byte) 0x00C6, (byte) 0x0076, (byte) 0x003F,
			(byte) 0x0001, (byte) 0x00C4, (byte) 0x00C2, (byte) 0x00C2,
			(byte) 0x00CC, (byte) 0x00C8, (byte) 0x00F0, (byte) 0x00E1,
			(byte) 0x00E3, (byte) 0x0007, (byte) 0x0086, (byte) 0x000F,
			(byte) 0x00EC, (byte) 0x003F, (byte) 0x0018, (byte) 0x00BE,
			(byte) 0x007F, (byte) 0x00FF, (byte) 0x00CE, (byte) 0x00F0,
			(byte) 0x00FE, (byte) 0x00FD, (byte) 0x007B, (byte) 0x0060,
			(byte) 0x00A0, (byte) 0x0031, (byte) 0x0082, (byte) 0x00D9,
			(byte) 0x00EF, (byte) 0x00DE, (byte) 0x00BD, (byte) 0x0063,
			(byte) 0x0060, (byte) 0x0060, (byte) 0x0062, (byte) 0x0063,
			(byte) 0x0078, (byte) 0x0079, (byte) 0x0063, (byte) 0x0013,
			(byte) 0x00C3, (byte) 0x00B3, (byte) 0x0013, (byte) 0x0037,
			(byte) 0x0019, (byte) 0x00FE, (byte) 0x00FC, (byte) 0x00FD,
			(byte) 0x00C7, (byte) 0x00C0, (byte) 0x002A, (byte) 0x006C,
			(byte) 0x00C8, (byte) 0x00C0, (byte) 0x00A1, (byte) 0x00EF,
			(byte) 0x00CE, (byte) 0x00F0, (byte) 0x00E1, (byte) 0x00C3,
			(byte) 0x0067, (byte) 0x0006, (byte) 0x0090, (byte) 0x005E,
			(byte) 0x0080, (byte) 0x0000, (byte) 0x0062, (byte) 0x0061,
			(byte) 0x0063, (byte) 0x0065, (byte) 0x0064, (byte) 0x0090,
			(byte) 0x0096, (byte) 0x0092, (byte) 0x0062, (byte) 0x0090,
			(byte) 0x0012, (byte) 0x00E7, (byte) 0x0066, (byte) 0x0060,
			(byte) 0x0065, (byte) 0x007B, (byte) 0x00CD, (byte) 0x0020,
			(byte) 0x0024, (byte) 0x0024, (byte) 0x0004, (byte) 0x000E,
			(byte) 0x0083, (byte) 0x00D7, (byte) 0x00AF, (byte) 0x005F,
			(byte) 0x0033, (byte) 0x0088, (byte) 0x008A, (byte) 0x0042,
			(byte) 0x00BC, (byte) 0x00F3, (byte) 0x00FE, (byte) 0x00E2,
			(byte) 0x0003, (byte) 0x0006, (byte) 0x0075, (byte) 0x00A3,
			(byte) 0x002B, (byte) 0x000C, (byte) 0x006F, (byte) 0x00AE,
			(byte) 0x00DE, (byte) 0x0061, (byte) 0x0078, (byte) 0x00FA,
			(byte) 0x0095, (byte) 0x008F, (byte) 0x0041, (byte) 0x005C,
			(byte) 0x005C, (byte) 0x0082, (byte) 0x00E1, (byte) 0x002B,
			(byte) 0x0003, (byte) 0x002F, (byte) 0x0003, (byte) 0x001B,
			(byte) 0x00EB, (byte) 0x0003, (byte) 0x0006, (byte) 0x0080,
			(byte) 0x0000, (byte) 0x0062, (byte) 0x0062, (byte) 0x0065,
			(byte) 0x0061, (byte) 0x0002, (byte) 0x0047, (byte) 0x0013,
			(byte) 0x0008, (byte) 0x0080, (byte) 0x0034, (byte) 0x00C2,
			(byte) 0x0000, (byte) 0x0032, (byte) 0x009B, (byte) 0x0081,
			(byte) 0x0089, (byte) 0x0085, (byte) 0x00E1, (byte) 0x00FB,
			(byte) 0x00E3, (byte) 0x00AB, (byte) 0x000C, (byte) 0x001F,
			(byte) 0x002E, (byte) 0x00DF, (byte) 0x0007, (byte) 0x00C6,
			(byte) 0x00CE, (byte) 0x001F, (byte) 0x00B8, (byte) 0x0030,
			(byte) 0x0048, (byte) 0x002F, (byte) 0x0040, (byte) 0x0000,
			(byte) 0x0001, (byte) 0x00C3, (byte) 0x0080, (byte) 0x0091,
			(byte) 0x00E1, (byte) 0x0023, (byte) 0x00D0, (byte) 0x00DF,
			(byte) 0x009F, (byte) 0x00D8, (byte) 0x007F, (byte) 0x0081,
			(byte) 0x009D, (byte) 0x000D, (byte) 0x004A, (byte) 0x0050,
			(byte) 0x0020, (byte) 0x002F, (byte) 0x00FC, (byte) 0x00F8,
			(byte) 0x00F1, (byte) 0x0083, (byte) 0x00E1, (byte) 0x00ED,
			(byte) 0x00DB, (byte) 0x00B7, (byte) 0x000C, (byte) 0x00CC,
			(byte) 0x002C, (byte) 0x001C, (byte) 0x000C, (byte) 0x003F,
			(byte) 0x0059, (byte) 0x0064, (byte) 0x0018, (byte) 0x008E,
			(byte) 0x005D, (byte) 0x00B2, (byte) 0x0060, (byte) 0x00F8,
			(byte) 0x00C5, (byte) 0x00F2, (byte) 0x0083, (byte) 0x0041,
			(byte) 0x0096, (byte) 0x004F, (byte) 0x0091, (byte) 0x00E1,
			(byte) 0x00DB, (byte) 0x00B7, (byte) 0x00EF, (byte) 0x000C,
			(byte) 0x009F, (byte) 0x003E, (byte) 0x007D, (byte) 0x0064,
			(byte) 0x0000, (byte) 0x00E9, (byte) 0x0005, (byte) 0x0008,
			(byte) 0x0020, (byte) 0x0016, (byte) 0x0056, (byte) 0x0016,
			(byte) 0x0046, (byte) 0x0006, (byte) 0x002E, (byte) 0x004E,
			(byte) 0x000E, (byte) 0x0006, (byte) 0x000E, (byte) 0x000E,
			(byte) 0x002E, (byte) 0x00A0, (byte) 0x001F, (byte) 0x00FF,
			(byte) 0x0033, (byte) 0x0070, (byte) 0x0072, (byte) 0x0072,
			(byte) 0x0082, (byte) 0x000D, (byte) 0x0000, (byte) 0x0061,
			(byte) 0x002E, (byte) 0x002E, (byte) 0x002E, (byte) 0x00B0,
			(byte) 0x0081, (byte) 0x006A, (byte) 0x0066, (byte) 0x0051,
			(byte) 0x000C, (byte) 0x0086, (byte) 0x004E, (byte) 0x00E9,
			(byte) 0x000C, (byte) 0x00FF, (byte) 0x0080, (byte) 0x0009,
			(byte) 0x00EB, (byte) 0x00C7, (byte) 0x0037, (byte) 0x0090,
			(byte) 0x00DF, (byte) 0x0099, (byte) 0x00C0, (byte) 0x007A,
			(byte) 0x0040, (byte) 0x007A, (byte) 0x0001, (byte) 0x0002,
			(byte) 0x0008, (byte) 0x00E8, (byte) 0x0002, (byte) 0x0026,
			(byte) 0x0006, (byte) 0x003E, (byte) 0x001E, (byte) 0x002E,
			(byte) 0x0006, (byte) 0x0036, (byte) 0x0076, (byte) 0x0076,
			(byte) 0x0006, (byte) 0x0001, (byte) 0x00A6, (byte) 0x00FF,
			(byte) 0x000C, (byte) 0x004C, (byte) 0x00AC, (byte) 0x001C,
			(byte) 0x0010, (byte) 0x00E7, (byte) 0x0001, (byte) 0x007D,
			(byte) 0x00C0, (byte) 0x000C, (byte) 0x0065, (byte) 0x0073,
			(byte) 0x00B0, (byte) 0x00FE, (byte) 0x0004, (byte) 0x00F9,
			(byte) 0x0002, (byte) 0x0022, (byte) 0x00CE, (byte) 0x00C5,
			(byte) 0x000A, (byte) 0x0014, (byte) 0x0067, (byte) 0x0007,
			(byte) 0x00EA, (byte) 0x00F9, (byte) 0x00CF, (byte) 0x0000,
			(byte) 0x00D2, (byte) 0x000B, (byte) 0x0010, (byte) 0x0040,
			(byte) 0x002C, (byte) 0x00DC, (byte) 0x00EC, (byte) 0x002C,
			(byte) 0x009C, (byte) 0x0097, (byte) 0x001F, (byte) 0x00BE,
			(byte) 0x0067, (byte) 0x0078, (byte) 0x00F1, (byte) 0x0081,
			(byte) 0x001D, (byte) 0x00E8, (byte) 0x006C, (byte) 0x0060,
			(byte) 0x0034, (byte) 0x00B2, (byte) 0x00B2, (byte) 0x0082,
			(byte) 0x00D3, (byte) 0x00EE, (byte) 0x00AF, (byte) 0x005F,
			(byte) 0x00BF, (byte) 0x0018, (byte) 0x00D8, (byte) 0x00D8,
			(byte) 0x00D8, (byte) 0x00C0, (byte) 0x00E9, (byte) 0x00F9,
			(byte) 0x00F7, (byte) 0x00EF, (byte) 0x00DF, (byte) 0x0040,
			(byte) 0x000B, (byte) 0x00D8, (byte) 0x0080, (byte) 0x0051,
			(byte) 0x00FA, (byte) 0x008F, (byte) 0x00E1, (byte) 0x00F7,
			(byte) 0x009F, (byte) 0x003F, (byte) 0x000C, (byte) 0x009C,
			(byte) 0x001C, (byte) 0x00EC, (byte) 0x000C, (byte) 0x00AF,
			(byte) 0x003F, (byte) 0x00FE, (byte) 0x0064, (byte) 0x0000,
			(byte) 0x00E9, (byte) 0x0005, (byte) 0x0008, (byte) 0x0020,
			(byte) 0x0096, (byte) 0x001B, (byte) 0x002F, (byte) 0x00DE,
			(byte) 0x005C, (byte) 0x00E9, (byte) 0x00DF, (byte) 0x00FA,
			(byte) 0x0043, (byte) 0x00EE, (byte) 0x00FB, (byte) 0x00AF,
			(byte) 0x003F, (byte) 0x0024, (byte) 0x00E5, (byte) 0x0042,
			(byte) 0x004E, (byte) 0x0036, (byte) 0x0016, (byte) 0x0086,
			(byte) 0x0067, (byte) 0x001F, (byte) 0x00BE, (byte) 0x003C,
			(byte) 0x0002, (byte) 0x0008, (byte) 0x0020, (byte) 0x0046,
			(byte) 0x004A, (byte) 0x00B3, (byte) 0x0033, (byte) 0x0040,
			(byte) 0x0080, (byte) 0x0001, (byte) 0x0000, (byte) 0x00DB,
			(byte) 0x00BD, (byte) 0x000A, (byte) 0x0038, (byte) 0x00BA,
			(byte) 0x0092, (byte) 0x0072, (byte) 0x005E, (byte) 0x0000,
			(byte) 0x0000, (byte) 0x0000, (byte) 0x0000, (byte) 0x0049,
			(byte) 0x0045, (byte) 0x004E, (byte) 0x0044, (byte) 0x00AE,
			(byte) 0x0042, (byte) 0x0060, (byte) 0x0082 };

	private String[] calendarHeader = { "Su", "Mo", "Tu", "We", "Th", "Fr",
			"Sa" };
	private String[] calendarNavigation = { "<< Previos", "Next >>" };

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private JLabel label = new JLabel();
	private Color backgroundColor = Color.white;
	private JTextField textField = new JTextField();
	private JButton button = new JButton(new ImageIcon(icon));

	private CalendarDialog datePickerWindow;

	// public CalendarPicker( JFrame owner )
	//
	// {
	// super( new GridBagLayout( ) ) ;
	// setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) ) ;
	//
	// datePickerWindow = new CalendarDialog( ) ;
	//
	// GridBagConstraints cTextField = new GridBagConstraints( ) ;
	// cTextField.fill = GridBagConstraints.HORIZONTAL ;
	// cTextField.weightx = 1 ;
	//
	// textField.setBackground( backgroundColor ) ;
	// textField.setPreferredSize( new Dimension( 92, 20 ) ) ;
	// button.setBorder( BorderFactory.createEmptyBorder( 4, 2, 4, 2 ) ) ;
	// button.setContentAreaFilled( false ) ;
	//
	// button.addActionListener( new ActionListener( ) {
	// public void actionPerformed( ActionEvent e ) {
	// button_action( ) ; } } ) ;
	//
	// add( label ) ;
	// add( textField, cTextField ) ;
	// add( button ) ;
	// }
	//
	// public CalendarPicker( JDialog owner )
	//
	// {
	// super( new GridBagLayout( ) ) ;
	// setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) ) ;
	//
	// datePickerWindow = new CalendarDialog( ) ;
	//
	// GridBagConstraints cTextField = new GridBagConstraints( ) ;
	// cTextField.fill = GridBagConstraints.HORIZONTAL ;
	// cTextField.weightx = 1 ;
	//
	// textField.setBackground( backgroundColor ) ;
	// textField.setPreferredSize( new Dimension( 92, 20 ) ) ;
	// button.setBorder( BorderFactory.createEmptyBorder( 4, 2, 4, 2 ) ) ;
	// button.setContentAreaFilled( false ) ;
	//
	// button.addActionListener( new ActionListener( ) {
	// public void actionPerformed( ActionEvent e ) {
	// button_action( ) ; } } ) ;
	//
	// add( label ) ;
	// add( textField, cTextField ) ;
	// add( button ) ;
	// }

	public CalendarPicker(SimpleDateFormat sdf)

	{
		super(new GridBagLayout());
		this.sdf = sdf;
		init();
	}

	public CalendarPicker()

	{
		super(new GridBagLayout());
		init();
	}

	private void init() {

		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		datePickerWindow = new CalendarDialog(sdf);

		GridBagConstraints cTextField = new GridBagConstraints();
		cTextField.fill = GridBagConstraints.HORIZONTAL;
		cTextField.weightx = 1;

		textField.setBackground(backgroundColor);
		textField.setPreferredSize(new Dimension(92, 20));
		button.setBorder(BorderFactory.createEmptyBorder(4, 2, 4, 2));
		button.setContentAreaFilled(false);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button_action();
			}
		});

		add(label);
		add(textField, cTextField);
		add(button);
	}

	public void setLabelText(String text)

	{
		label.setText(text);
	}

	public String getText()

	{
		return textField.getText();
	}

	public JTextField getTextFiled()

	{
		return textField;
	}

	public void setText(String text)

	{
		textField.setText(text);
	}

	public Date getPickedDate() throws ParseException {
		if (!textField.getText().trim().isEmpty()) {
			return (sdf.parse(textField.getText()));
		} else {
			return null;
		}
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public void setFont(Font font)

	{
		super.setFont(font);
		if (label != null) {
			label.setFont(font);
			textField.setFont(font);
		}
	}

	public void setForeground(Color color)

	{
		super.setForeground(color);
		if (label != null) {
			label.setForeground(color);
			textField.setForeground(color);
		}
	}

	public void setBackground(Color color)

	{
		if (textField != null) {
			backgroundColor = color;
			textField.setBackground(color);
		}
	}

	public void setEnabled(boolean enabled)

	{
		super.setEnabled(enabled);

		if (enabled) {
			textField.setBackground(backgroundColor);
			label.setForeground(textField.getForeground());
		} else {
			textField.setBackground(new Color(230, 230, 230));
			label.setForeground(Color.lightGray);
			textField.setText("");
		}

		textField.setEditable(enabled);
		button.setEnabled(enabled);
	}

	public void button_action()

	{
		datePickerWindow.setVisible(true);
		textField.setText(datePickerWindow.getTextdate().getText());
	}
}
