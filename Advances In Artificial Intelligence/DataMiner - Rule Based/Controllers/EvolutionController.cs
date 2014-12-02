using DataMiner.Classes;
using DataMiner.Helpers;
using System.Collections.Generic;

namespace DataMiner.Controllers
{
    public class EvolutionController
    {
        public List<Individual> SinglePointCrossover(List<Individual> parents)
        {
            if (RandomNumber.GenerateProbability() < Config.CrossoverProbability)
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

                if (RandomNumber.GenerateProbability() < Config.MutationProbability)
                {
                    // If the current value is the last value of a rule.
                    if ((i + 1) % Config.RuleLength == 0)
                    {
                        gene = (gene == 1 ? 0 : 1);
                    }
                    else
                    {
                        gene = this.ApplyCreep(gene);
                    }
                }

                newChromosome[i] = gene;
            }

            return newChromosome;
        }

        private double ApplyCreep(double value)
        {
            //double creep = RandomNumber.GenerateCreep() * Config.MutationLimiter;
            double creep = RandomNumber.GenerateNormal();

            if ((value + creep) > 1)
            {
                value = 1;
            }
            else if ((creep + value) < 0)
            {
                value = 0;
            }
            else
            {
                value += creep;
            }

            return value;
        }
    }
}
