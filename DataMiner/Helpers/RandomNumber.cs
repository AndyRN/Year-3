using System;

namespace DataMiner.Helpers
{
    public static class RandomNumber
    {
        private static Random r = new Random();

        public static int Generate(int max)
        {
            return r.Next(max);
        }

        public static double GenerateProbability()
        {
            return r.NextDouble() * 100;
        }
    }
}
