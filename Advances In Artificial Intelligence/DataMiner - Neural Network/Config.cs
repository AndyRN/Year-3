using System.Collections.Generic;
using System.IO;

namespace DataMiner
{
    public static class Config
    {
        public static readonly int NumberOfRuns = 1;

        public static readonly int PopulationSize = 100;
        public static readonly int Generations = 1000;
        public static readonly int TournamentSize = 10;
        public static readonly int CrossoverProbability = 90; // (x / 100) 1 = 1%
        public static readonly int MutationProbability = 2400; // (x / 100,000) 1,000 = 1%, 100 = 0.1%, 10 = 0.01%, 1 = 0.001%
        public static readonly int WildcardProbability = 50; // (x / 100) 1 = 1%
        public static readonly int RulesPerIndividual = 10;
        public static readonly int NumberOfInputs = 6;
        public static readonly int NumberOfHiddenNodes = 5;

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