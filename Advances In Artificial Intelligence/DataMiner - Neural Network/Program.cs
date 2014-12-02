using DataMiner.Classes;
using DataMiner.Controllers;
using DataMiner.Helpers;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace DataMiner
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.SetWindowSize(50, 15);

            Init(Selection());

            List<double> results = new List<double>();
            for (int i = 0; i < Config.NumberOfRuns; i++)
            {
                PrepareData();

                Individual bestSolution = RunLearningPhase();

                results.Add(RunTestingPhase(bestSolution));
            }

            OutputSummary(results);
        }

        private static string Selection()
        {
            Console.WriteLine("/// Data Miner \\\\\\");
            Console.WriteLine("\nSelect a file to mine:\n");
            Console.WriteLine("1 = \"data1.txt\"");
            Console.WriteLine("2 = \"data2.txt\"");
            Console.WriteLine("3 = \"data3.txt\"");
            Console.Write("\nChoice: ");

            return Console.ReadLine();
        }

        private static void Init(string response)
        {
            string dataFilePath = string.Empty;
            string dataFileName = string.Empty;

            switch (response[0])
            {
                case '1':
                    dataFilePath = "data1.txt";
                    Config.DataFileName = "data1";
                    Config.DataSize = 64;
                    Config.RuleLength = 7;
                    Config.ChromosomeLength = Config.RuleLength * Config.RulesPerIndividual;
                    break;
                case '2':
                    dataFilePath = "data2.txt";
                    Config.DataFileName = "data2";
                    Config.DataSize = 2048;
                    Config.RuleLength = 12;
                    Config.ChromosomeLength = Config.RuleLength * Config.RulesPerIndividual;
                    break;
                case '3':
                    dataFilePath = "data3.txt";
                    Config.DataFileName = "data3";
                    Config.DataSize = 2000;
                    Config.RuleLength = 7;
                    Config.ChromosomeLength = ((Config.NumberOfInputs * Config.NumberOfHiddenNodes) + Config.NumberOfHiddenNodes) + (Config.NumberOfHiddenNodes + 1);
                    break;
                default:
                    break;
            }

            Config.FullData = File.ReadAllLines(dataFilePath).ToList();

            Config.DirectoryPath = string.Format("D:\\Uni - Computer Science\\AiA\\Assignment\\Results\\{0}\\{1}",
                Config.DataFileName,
                DateTime.Now.ToString().Replace('/', '-').Replace(':', '-'));

            Directory.CreateDirectory(Config.DirectoryPath);
        }

        private static void PrepareData()
        {
            List<string> shuffledData = new List<string>(Config.FullData);
            ListShuffler.Shuffle(shuffledData);

            int range = (int)(shuffledData.Count * 0.8);

            Config.LearningData = shuffledData.GetRange(0, range);
            Config.TestData = shuffledData.GetRange(range, shuffledData.Count - range);
        }

        private static Individual RunLearningPhase()
        {
            Console.WriteLine("\n\n/// Learning Phase \\\\\\");

            PopulationController pController = new PopulationController();
            StringBuilder csv = new StringBuilder();

            // Initialise the population.
            List<Individual> population = pController.GeneratePopulation();
            population = pController.CalculateFitness(population);

            Console.WriteLine(string.Format("\n\n[Initial Population]\n\nTotal fitness: {0}\nBest fitness: {1}\nMean fitness : {2}",
                pController.GetTotalFitness(population),
                pController.GetBestFitness(population),
                pController.GetMeanFitness(population)));

            // Get the current best solution.
            Individual bestSolution = pController.GetFittestIndividual(population);

            // Run for however many generations has been set.
            for (int i = 1; i <= Config.Generations; i++)
            {
                Console.WriteLine("\n\n-----");
                Console.WriteLine(string.Format("\n\n- Generation {0}", i));

                // Select a new set of the current population.
                population = pController.TournamentSelect(population);

                // Evolve the newly selected population in an attempt to yeild fitter individuals.
                population = pController.ExecuteCrossover(population);
                population = pController.ExecuteMutation(population);

                //// Alter the weights of the population in order to reduce the error.
                //population = pController.AdjustWeights(population);

                // Recalculate the fitness of the individuals in the new population.
                population = pController.CalculateFitness(population);

                // If the best solution was lost, replace the worst solution with it.
                if (pController.GetBestFitness(population) < bestSolution.Fitness)
                {
                    population = pController.ReplaceWorstIndividual(population, bestSolution);
                }
                else if (pController.GetBestFitness(population) > bestSolution.Fitness)
                {
                    bestSolution = pController.GetFittestIndividual(population);
                }

                Console.WriteLine(string.Format("\n\nTotal fitness: {0}\nBest fitness: {1} / {2} ({3}%)\nMean fitness : {4}",
                    pController.GetTotalFitness(population),
                    pController.GetBestFitness(population), Config.LearningData.Count, ((double)pController.GetBestFitness(population) / (double)Config.LearningData.Count) * 100,
                    pController.GetMeanFitness(population)));

                csv.Append(string.Format("{0},{1},{2}{3}",
                    i,
                    pController.GetBestFitness(population),
                    pController.GetMeanFitness(population),
                    Environment.NewLine));

                if (pController.GetBestFitness(population) == Config.LearningData.Count)
                {
                    Console.WriteLine("\n\n=== Optimum Solution found ===");
                    break;
                }
            }

            Console.WriteLine("\n\n\\\\\\ Finished Learning ///");

            string outputFilePath = string.Format("{0}\\{1}.{2}.csv",
                Config.DirectoryPath,
                DateTime.Now.ToLongTimeString().Replace(':', '-'),
                DateTime.Now.Millisecond);

            Config.RunFile = new FileInfo(outputFilePath);

            File.WriteAllText(Config.RunFile.FullName, csv.ToString());

            return bestSolution;
        }

        private static double RunTestingPhase(Individual bestSolution)
        {
            Console.WriteLine("\n\n/// Testing Phase \\\\\\");

            TestController tController = new TestController(bestSolution);

            // Test the solution against each line of the data.
            foreach (string line in Config.TestData)
            {
                tController.TestSolution(line);
            }

            bestSolution.SuccessRate = tController.CalculateSuccessRate();

            OutputBestSolution(bestSolution);

            Console.WriteLine(string.Format("\n\nSuccess Rate: {0}%", bestSolution.SuccessRate));

            Console.WriteLine("\n\n\\\\\\ Finished Testing ///");

            return bestSolution.SuccessRate;
        }

        private static void OutputBestSolution(Individual bestSolution)
        {
            StringBuilder csv = new StringBuilder();

            csv.Append(string.Format("{0}{1}", Environment.NewLine, "'"));

            for (int i = 0; i < bestSolution.Chromosome.Length; i++)
            {
                csv.Append(string.Format("{0}{1}{2}", bestSolution.Chromosome[i], Environment.NewLine, "'"));
            }

            // Append the success rate of the solution.
            csv.Append(string.Format("{0}{1}%", Environment.NewLine, bestSolution.SuccessRate));

            File.AppendAllText(Config.RunFile.FullName, csv.ToString());
        }

        private static void OutputSummary(List<double> results)
        {
            StringBuilder csv = new StringBuilder();

            csv.Append(string.Format("{0},{1},{2},{3},{4},{5},{6}",
                Config.PopulationSize,
                Config.Generations,
                Config.TournamentSize,
                Config.CrossoverProbability,
                Config.MutationProbability,
                results.Average(),
                Environment.NewLine));

            FileInfo summaryFile = new FileInfo(string.Format("D:\\Uni - Computer Science\\AiA\\Assignment\\Results\\{0}\\Summary.csv", Config.DataFileName));

            File.AppendAllText(summaryFile.FullName, csv.ToString());
        }
    }
}
