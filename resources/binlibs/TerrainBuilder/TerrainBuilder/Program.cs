﻿using System;
using System.Windows.Forms;

namespace TerrainBuilder
{
    class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Console.Title = EmbeddedFiles.AppName;
            new WindowVisualize().Run(40, 60);
        }
    }
}
