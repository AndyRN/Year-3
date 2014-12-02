using DataMiner.Classes;
using DataMiner.Helpers;
using System.Collections.Generic;

namespace DataMiner.Controllers
{
    public class EvolutionController
    {
        public List<Individual> SinglePointCrossover(List<Individual> parents)
        {
            if (RandomNumber.Generate(100) < Config.CrossoverProbability)
            {
                List<Individual> children = new List<Individual>();
                Individual parent1 = parents[0];
                Individual parent2 = parents[1];

                int crossoverPoint = RandomNumber.Generate(Config.ChromosomeLength);

                // Create a new chromosome with the head of the first chromosome, then append the tail of the second chromosome to it.
                double[] newChromosome1 = parent1.Chromosome.GetRange(0, crossoverPoint);
                newChromosome1 = newChromosome1.Append(parent2.Chromosome.GetRange(crossoverPoint, Config.ChromosomeLength - crossoverPoint));

                // Create another new chromosome with the head of the second chromosome, then append the tail of the first chromosome to it.
                double[] newChromosome2 = parent2.Chromosome.GetRange(0, crossoverPoint);
                newChromosome2 = newChromosome2.Append(parent1.Chromosome.GetRange(crossoverPoint, Config.ChromosomeLength - crossoverPoint));

                // Add the newly created chromosomes to the children.
                children.Add(new Individual(newChromosome1));
                children.Add(new Individual(newChromosome2));

                return children;
            }

            // Crossover did not occur, just return the parents.
            return parents;
        }

        public double[] BitwiseMutation(Individual individual)
        {
            double[] newChromosome = new double[Config.ChromosomeLength];

            for (int i = 0; i < individual.Chromosome.Length; i++)
            {
                double gene = individual.Chromosome[i];

                if (RandomNumber.Generate(100000) < Config.MutationProbability)
                {
                    gene = RandomNumber.GenerateDouble();
                }

                newChromosome[i] = gene;
            }

            return newChromosome;
        }
    }
}
