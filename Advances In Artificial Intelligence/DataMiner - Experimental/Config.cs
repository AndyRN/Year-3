﻿using System.Collections.Generic;
using System.IO;

namespace DataMiner
{
    public static class Config
    {
        public static readonly int NumberOfRuns = 10;

        public static readonly int PopulationSize = 100;
        public static readonly int MaxGenerations = 5000;
        public static readonly int TournamentSize = 5;
        public static readonly double CrossoverProbability = 90;
        public static readonly double MutationProbability = 2.86;
        public static readonly double WildcardProbability = 50;
        public static readonly int RulesPerIndividual = 5;

        public static int DataSize;
        public static int ChromosomeLength;
        public static int RuleLength;

        public static List<string> FullData;
        public static List<string> LearningData;
        public static List<string> TestData;

        public static string DirectoryPath;
        public static string DataFileName;
        public static FileInfo RunFile;
    }
}
