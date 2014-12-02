using DataMiner.Classes;
using DataMiner.Helpers;
using System.Collections.Generic;
using System.Linq;

namespace DataMiner.Controllers
{
    public class PopulationController
    {
        public List<Individual> GeneratePopulation()
        {
            List<Individual> population = new List<Individual>();

            for (int i = 0; i < Config.PopulationSize; i++)
            {
                population.Add(new Individual());
            }

            return population;
        }

        public int GetTotalFitness(List<Individual> population)
        {
            return population.Sum(x => x.Fitness);
        }

        public int GetBestFitness(List<Individual> population)
        {
            return population.Max(x => x.Fitness);
        }

        public double GetMeanFitness(List<Individual> population)
        {
            return population.Average(x => x.Fitness);
        }

        public Individual GetFittestIndividual(List<Individual> population)
        {
            int bestFitness = population.Max(x => x.Fitness);
            Individual fittestIndividual = population.Where(x => x.Fitness == bestFitness).FirstOrDefault();

            return fittestIndividual.Clone();
        }

        public List<Individual> ReplaceWorstIndividual(List<Individual> population, Individual bestSolution)
        {
            population.Shuffle();

            Individual randomIndividual = population.FirstOrDefault();
            randomIndividual.Fitness = bestSolution.Fitness;
            randomIndividual.Chromosome = bestSolution.Chromosome;

            return population;
        }

        public List<Individual> TournamentSelect(List<Individual> population)
        {
            List<Individual> offspring = new List<Individual>();

            while (offspring.Count < Config.PopulationSize)
            {
                List<Individual> tournament = new List<Individual>();

                // Pick T (selectionSize) individuals at random from the population.
                while (tournament.Count < Config.TournamentSize)
                {
                    tournament.Add(population[RandomNumber.Generate(Config.PopulationSize)]);
                }

                // Select the fittest from the current selection and add to the offspring.
                int fittestValue = tournament.Max(x => x.Fitness);
                Individual fittestIndividual = tournament.Where(x => x.Fitness == fittestValue).FirstOrDefault();
                offspring.Add(fittestIndividual);
            }

            return offspring;
        }

        public List<Individual> ExecuteCrossover(List<Individual> population)
        {
            EvolutionController eController = new EvolutionController();

            List<Individual> offspring = new List<Individual>();

            // Shuffle the order of the population before selecting parents.
            ListShuffler.Shuffle(population);

            List<Individual> parents = new List<Individual>();
            foreach (Individual individual in population)
            {
                parents.Add(individual);

                if (parents.Count < 2)
                {
                    continue;
                }

                // Commence Single Point Crossover on two parents to create two children with swapped tails by a given probability.
                offspring.AddRange(eController.SinglePointCrossover(parents));

                parents.Clear();
            }

            if (parents.Count != 0)
            {
                offspring.AddRange(parents);
            }

            return offspring;
        }

        public List<Individual> ExecuteMutation(List<Individual> population)
        {
            EvolutionController eController = new EvolutionController();

            // Bitwise mutate every gene in each individual's chromosome by a given probability.
            foreach (Individual individual in population)
            {
                individual.UpdateChromosome(eController.BitwiseMutation(individual));
            }

            return population;
        }

        public List<Individual> CalculateFitness(List<Individual> population)
        {
            foreach (Individual individual in population)
            {
                individual.EvaluateFitness();
            }

            return population;
        }       
    }
}
