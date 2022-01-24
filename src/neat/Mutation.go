//TODO: FOR ALL GO CLASSES, MAKE SURE TO HAVE THE CHECK THAT ALL GO FUNCTIONS ARE DONE BEFORE PROCEEDING
package neat

import (
	"math/rand"
	"time"
)
type Marking struct {
	order int
	source int 
	destination int
}

func newMarking() *Marking {
	m := new(Marking)
	m.order = 0
	m.source = 0
	m.destination = 0
	return m
}

type Mutation struct {
	MUTATE_LINK float64
	MUTATE_NODE float64
	MUTATE_ENABLE float64
	MUTATE_DISABLE float64
	MUTATE_WEIGHT float64
	PETRUB_CHANCE float64
	SHIFT_STEP float64
	historical []*Marking
}

func newMutation() *Mutation {
	m := new(Mutation)
	m.MUTATE_LINK = 0.2
	m.MUTATE_NODE = 0.1
	m.MUTATE_ENABLE = 0.6
	m.MUTATE_DISABLE = 0.2
	m.MUTATE_WEIGHT = 2.0
	m.PETRUB_CHANCE = 0.9
	m.SHIFT_STEP = 0.1
	m.historical = make([]*Marking, 0)
	return m
}

//registers a new marking in the historical array
func (m *Mutation) RegisterMarking(info EdgeInfo) int{
	//iterates over m.historical and returns the index of the first element that matches the info
	for i, e := range m.historical {
		if e.source == info.source && e.destination == info.destination {
			return i
		}
	}

	creation := newMarking()
	creation.order = len(m.historical)
	creation.source = info.source
	creation.destination = info.destination

	m.historical = append(m.historical, creation)

	return len(m.historical) - 1
}

//mutates all the genotypes
func (m *Mutation) MutateAll(genotype Genotype) {
	//seed the random number generator
	rand.Seed(time.Now().UnixNano())
	m.MUTATE_LINK = 0.2
	m.MUTATE_NODE = 0.1
	m.MUTATE_ENABLE = 0.6
	m.MUTATE_DISABLE = 0.2
	m.MUTATE_WEIGHT = 2.0

	p := m.MUTATE_WEIGHT

	go func() {
		for p > 0 {
			roll := rand.Float64()
			if (roll < p) {
				m.MutateWeight(genotype)
			}
	
			p--
		}
	}()
		
	go func() {
		p = m.MUTATE_LINK
		for p > 0 {
			roll := rand.Float64()
			if (roll < p) {
				m.MutateLink(genotype)
			}
	
			p--
		}
	}()
	
	go func() {
		p = m.MUTATE_NODE
		for p > 0 {
			roll := rand.Float64()
			if (roll < p) {
				m.MutateNode(genotype)
			}

			p--
		}
	}()
	
	go func() {
		p = m.MUTATE_DISABLE
		for p > 0 {
			roll := rand.Float64()
			if (roll < p) {
				m.MutateDisable(genotype)
			}

			p--
		}
	}()
	
	go func() {
		p = m.MUTATE_ENABLE
		for p > 0 {
			roll := rand.Float64()
			if (roll < p) {
				m.MutateEnable(genotype)
			}

			p--
		}
	}()	
}


//MutateLink
func (m *Mutation) MutateLink(genotype Genotype) {
	potential := make([]*EdgeInfo, 0)
	//loops through both the vertices and the edges
	for i := 0; i < len(genotype.vertices); i++ {
		for j := 0; j < len(genotype.vertices); j++ {
			source := genotype.vertices[i].index
			destination := genotype.vertices[j].index

			t1 := genotype.vertices[i].etype
			t2 := genotype.vertices[j].etype

			if (t1 == ETypeOutput  || t2 == ETypeInput) {
				continue
			}

			if (source == destination) {
				continue
			}

			search := false

			//loops through the edges
			for k := 0; k < len(genotype.edges); k++ {
				if (genotype.edges[k].source == source && genotype.edges[k].destination == destination) {
					search = true
					break
				}
			}

			if (!search) {
				weight := rand.Float64() * 4.0 - 2.0
				creation := newEdgeInfo(source, destination, weight, true)
				
				potential = append(potential, creation)
			}
		}
	}

	if (len(potential) <= 0) {
		return
	}

	selection := rand.Intn(len(potential))

	mutation := potential[selection]
	mutation.innovation = m.RegisterMarking(*mutation)

	genotype.AddEdge(mutation.source, mutation.destination, mutation.weight, mutation.enabled, mutation.innovation)
}

//MutateNode
func (m *Mutation) MutateNode(genotype Genotype) {
	selection := rand.Intn(len(genotype.edges))
	edge := genotype.edges[selection]

	if (!edge.enabled) {
		return 
	}

	edge.enabled = false

	vertex_new := genotype.vertices[len(genotype.vertices) - 1].index + 1

	vertex := newVertex(ETypeHidden, vertex_new)

	first := newEdgeInfo(edge.source, vertex_new, 1.0, true)
	second := newEdgeInfo(vertex_new, edge.destination, edge.weight, true)

	first.innovation = m.RegisterMarking(*first)
	second.innovation = m.RegisterMarking(*second)

	genotype.AddVertex(vertex.etype, vertex.index)

	genotype.AddEdge(first.source, first.destination, first.weight, first.enabled, first.innovation)
	genotype.AddEdge(second.source, second.destination, second.weight, second.enabled, second.innovation)
}

//MutateDisable
func (m *Mutation) MutateDisable(genotype Genotype) {
	candidates := make([]*EdgeInfo, 0)

	for _, e := range genotype.edges {
		if (e.enabled) {
			candidates = append(candidates, e)
		}
	}

	if (len(candidates) == 0) {
		return
	}

	selection := rand.Intn(len(candidates))

	edge := candidates[selection]
	edge.enabled = false
}

//MutateEnable
func (m *Mutation) MutateEnable(genotype Genotype) {
	candidates := make([]*EdgeInfo, 0)

	for _, e := range genotype.edges {
		if (!e.enabled) {
			candidates = append(candidates, e)
		}
	}

	if (len(candidates) == 0) {
		return
	}

	selection := rand.Intn(len(candidates))

	edge := candidates[selection]
	edge.enabled = true
}

//MutateWeight
func (m *Mutation) MutateWeight(genotype Genotype) {
	selection := rand.Intn(len(genotype.edges))

	edge := genotype.edges[selection]

	roll := rand.Float64()

	if (roll < m.PETRUB_CHANCE) {
		m.MutateWeightShift(edge, m.SHIFT_STEP)
	} else {
		m.MutateWeightRandom(edge)
	}
}

func (m *Mutation) MutateWeightShift(edge *EdgeInfo, step float64) {
	scalar := rand.Float64() * step - step * 0.5
	edge.weight += scalar
}

func (m *Mutation) MutateWeightRandom(edge *EdgeInfo) {
	value := rand.Float64() * 4.0 - 2.0
	edge.weight = value
}
